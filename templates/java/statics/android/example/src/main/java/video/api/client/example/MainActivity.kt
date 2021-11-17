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
import video.api.client.ApiVideoClient
import video.api.client.api.ApiException
import video.api.client.api.models.Environment
import video.api.client.api.models.Video
import video.api.client.api.models.VideoCreationPayload
import video.api.client.api.upload.UploadProgressListener
import video.api.client.example.databinding.ActivityMainBinding
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
            val apiVideoClient = ApiVideoClient(
                PreferenceManager.getDefaultSharedPreferences(this)
                    .getString(getString(R.string.api_key_key), null),
                if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean(
                        getString(R.string.environment_key),
                        true
                    )
                ) {
                    Environment.SANDBOX
                } else {
                    Environment.PRODUCTION
                }
            ).apply {
                this.httpClient.writeTimeout = 60000 // 1 min
            }

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
                    var video =
                        apiVideoClient.videos().create(
                            VideoCreationPayload().title(
                                PreferenceManager.getDefaultSharedPreferences(this)
                                    .getString(getString(R.string.title_key), null)
                            )
                        )
                    video = apiVideoClient.videos().upload(
                        video.videoId,
                        videoFile
                    ) { bytesWritten, totalBytes, _, _ ->
                        runOnUiThread {
                            binding.progressBar.progress =
                                (100 * bytesWritten / totalBytes).toInt()
                        }
                    }
                    onUploadSuccess(video)
                } catch (e: Exception) {
                    onUploadFailed(e)
                }
            }
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
        if (e is ApiException) {
            Log.e(getString(R.string.app_name), "Status code: " + e.code)
        }
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