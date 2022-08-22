package video.api.uploader.example

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceManager
import video.api.uploader.api.models.Environment
import video.api.uploader.api.models.Video
import video.api.uploader.api.services.UploadService
import video.api.uploader.api.services.UploadServiceListener
import video.api.uploader.example.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), UploadServiceListener {
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var service: UploadService
    private val environment: Environment
        get() = if (PreferenceManager.getDefaultSharedPreferences(applicationContext)
                .getBoolean(
                    getString(R.string.environment_key),
                    true
                )
        ) {
            Environment.SANDBOX
        } else {
            Environment.PRODUCTION
        }
    private val token: String
        get() = PreferenceManager.getDefaultSharedPreferences(applicationContext)
            .getString(getString(R.string.token_key), getString(R.string.default_token))!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settingsLayout, SettingsFragment())
            .commit()

        startService()

        binding.pickFiles.setOnClickListener {
            Log.i(getString(R.string.app_name), "Checking permissions")
            when {
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED -> {
                    launchFilePickerIntent()
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
        }

        binding.cancel.setOnClickListener { service.cancelAll() }
    }

    private fun launchFilePickerIntent() {
        val intent = Intent(Intent.ACTION_PICK).apply {
            type = "video/mp4"
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        }
        filesPickerResult.launch(intent)
    }

    private fun startService() {
        UploadService.startService(
            this,
            CustomUploaderService::class.java,
            apiKey = null, // As we are using upload token we don't require the API key
            environment = environment,
            timeout = 60000, // 1 min
            onServiceCreated = { service ->
                this.service = service
                this.service.addListener(this)
            },
            onServiceDisconnected = { Log.e(TAG, "Upload service has been disconnected") }
        )
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                launchFilePickerIntent()
            } else {
                Utils.showAlertDialog(
                    this,
                    getString(R.string.permission),
                    getString(R.string.permission_required)
                )
            }
        }

    private var filesPickerResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                binding.progressBar.visibility = View.VISIBLE

                result.data?.let { data ->
                    // Multiple files
                    data.clipData?.let { clipData ->
                        for (i in 0 until clipData.itemCount) {
                            clipData.getItemAt(i).uri?.let { uri ->
                                val path = Utils.getFilePath(this, uri)!!
                                service.uploadWithUploadToken(token, path)
                            }
                        }
                    } ?: run {
                        // Single file
                        data.data?.let { uri ->
                            val path = Utils.getFilePath(this, uri)!!
                            service.uploadWithUploadToken(token, path)
                        }
                    }
                } ?: Log.e(TAG, "No data received")
            }
        }


    override fun onUploadStarted(id: String) {
        Log.i(TAG, "Started to upload: $id")

        runOnUiThread {
            binding.progressBar.visibility = View.VISIBLE
            binding.progressBar.progress = 0
            binding.cancel.visibility = View.VISIBLE
        }
    }

    override fun onUploadComplete(video: Video) {
        Log.i(
            TAG,
            "File has been successfully upload: ${video.videoId}"
        )

        runOnUiThread {
            Utils.showAlertDialog(
                this,
                getString(R.string.success),
                getString(R.string.file_uploaded)
            )
        }
    }

    override fun onUploadProgress(id: String, progress: Int) {
        runOnUiThread {
            binding.progressBar.progress = progress
        }
    }

    override fun onUploadError(id: String, e: Exception) {
        Log.i(
            TAG,
            "Failed to send send $id",
            e
        )

        runOnUiThread {
            Utils.showAlertDialog(
                this,
                getString(R.string.error),
                getString(R.string.upload_failed, e.message ?: e)
            )
        }
    }

    override fun onUploadCancelled(id: String) {
        // Nothing
    }

    override fun onLastUpload() {
        Log.i(TAG, "Last upload")

        runOnUiThread {
            binding.progressBar.visibility = View.GONE
            binding.cancel.visibility = View.GONE
        }
    }

    companion object {
        private const val TAG = "UploadActivity"
    }
}