package video.api.client.example

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceManager
import video.api.client.example.databinding.ActivityMainBinding
import video.api.uploader.VideosApi
import video.api.uploader.api.ApiClient
import video.api.uploader.api.ApiException
import video.api.uploader.api.models.Environment
import video.api.uploader.api.models.Video
import video.api.uploader.api.upload.UploadProgressListener
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val executor: ExecutorService = Executors.newSingleThreadExecutor()
    private var uploadTask: Future<*>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settingsLayout, SettingsFragment())
            .commit()

        binding.uploadButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                Log.i(getString(R.string.app_name), "onUploadFile")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    when {
                        ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        ) == PackageManager.PERMISSION_GRANTED -> {
                            uploadFile()
                        }
                        shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE) -> {
                            Utils.showAlertDialog(
                                this,
                                getString(R.string.permission),
                                getString(R.string.permission_required)
                            )
                            requestPermissionLauncher.launch(
                                Manifest.permission.READ_EXTERNAL_STORAGE
                            )
                        }
                        else -> {
                            requestPermissionLauncher.launch(
                                Manifest.permission.READ_EXTERNAL_STORAGE
                            )
                        }
                    }
                } else { // No need for permission
                    uploadFile()
                }
            } else {
                uploadTask?.cancel(true)
            }
        }
    }

    private fun uploadFile() {
        val filePath = Utils.getFilePathFromPrefs(this)
        if (filePath.isNullOrBlank()) {
            Utils.showAlertDialog(
                this,
                getString(R.string.success),
                getString(R.string.no_file)
            )
        } else {
            val basePath = if (PreferenceManager.getDefaultSharedPreferences(applicationContext)
                    .getBoolean(
                        getString(R.string.environment_key),
                        true
                    )
            ) {
                Environment.SANDBOX
            } else {
                Environment.PRODUCTION
            }.basePath

            val apiClient = if (PreferenceManager.getDefaultSharedPreferences(this)
                    .getBoolean(getString(R.string.auth_type_key), true)
            ) {
                ApiClient(PreferenceManager.getDefaultSharedPreferences(this)
                    .getString(getString(R.string.api_key_key), null), basePath)
            } else {
                ApiClient(basePath)
            }.apply {
                writeTimeout = 60000 // 1 min
            }
            val videoApi = VideosApi(apiClient)

            binding.progressBar.visibility = View.VISIBLE
            binding.progressBar.progress = 0
            /**
             *  Dispatch API calls from main thread to avoid to stuck main thread:
             *  Use executor or create a thread.
             */
            uploadTask = executor.submit {
                try {
                    Log.i(getString(R.string.app_name), "Upload file: $filePath")
                    val videoFile = File(filePath)
                    val video = if (PreferenceManager.getDefaultSharedPreferences(this)
                            .getBoolean(getString(R.string.auth_type_key), true)
                    ) {
                        val videoId = PreferenceManager.getDefaultSharedPreferences(this)
                            .getString(getString(R.string.video_id_key), null)
                        videoApi.upload(videoId, videoFile, progressListener)
                    } else {
                        val token = PreferenceManager.getDefaultSharedPreferences(this)
                            .getString(getString(R.string.token_key), null)
                        videoApi.uploadWithUploadToken(token, videoFile, progressListener)
                    }
                    onUploadSuccess(video)
                } catch (e: Exception) {
                    onUploadFailed(e)
                }
            }
        }
    }

    private val progressListener =
        UploadProgressListener { bytesWritten, totalBytes, _, _ ->
            runOnUiThread {
                binding.progressBar.progress =
                    (100 * bytesWritten / totalBytes).toInt()
            }
        }

    private fun onUploadSuccess(video: Video) {
        runOnUiThread {
            binding.uploadButton.isChecked = false
            binding.progressBar.visibility = View.GONE
            Utils.showAlertDialog(
                this,
                getString(R.string.success),
                getString(R.string.file_uploaded)
            )
        }
        Log.i(getString(R.string.app_name), "File has been successfully upload: $video")
    }

    private fun onUploadFailed(e: Exception) {
        runOnUiThread {
            binding.uploadButton.isChecked = false
            binding.progressBar.visibility = View.GONE
            Utils.showAlertDialog(
                this,
                getString(R.string.error),
                getString(R.string.upload_failed, e.message ?: e)
            )
        }
        Log.e(getString(R.string.app_name), "Exception when calling VideoApi")
        Log.e(getString(R.string.app_name), "Reason: " + e.message, e)
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                uploadFile()
            } else {
                Utils.showAlertDialog(
                    this,
                    getString(R.string.permission),
                    getString(R.string.permission_required)
                )
            }
        }
}