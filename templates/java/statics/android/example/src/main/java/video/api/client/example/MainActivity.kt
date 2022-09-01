package video.api.client.example

import android.Manifest
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceManager
import video.api.client.ApiVideoClient
import video.api.client.api.ApiCallback
import video.api.client.api.ApiException
import video.api.client.api.models.Environment
import video.api.client.api.models.Video
import video.api.client.api.models.VideoCreationPayload
import video.api.client.api.services.UploadService
import video.api.client.api.services.UploadServiceListener
import video.api.client.example.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), UploadServiceListener {
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var service: UploadService
    private lateinit var serviceConnection: ServiceConnection
    private val client: ApiVideoClient by lazy { ApiVideoClient(apiKey, environment) }
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
    private val apiKey: String?
        get() = PreferenceManager.getDefaultSharedPreferences(applicationContext)
            .getString(getString(R.string.api_key_key), null)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settingsLayout, SettingsFragment())
            .commit()

        bindService()

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

    override fun onDestroy() {
        super.onDestroy()
        unbindService()
    }

    private fun launchFilePickerIntent() {
        val intent = Intent(Intent.ACTION_PICK).apply {
            type = "video/mp4"
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        }
        filesPickerResult.launch(intent)
    }

    private fun bindService() {
        serviceConnection = UploadService.startService(
            this,
            CustomUploaderService::class.java,
            apiKey = apiKey,
            environment = environment,
            timeout = 60000, // 1 min
            { service ->
                this.service = service
                this.service.addListener(this)
            },
            { Log.e(TAG, "Upload service has been disconnected") }
        )
    }

    private fun unbindService() {
        this.service.removeListener(this)
        UploadService.unbindService(this, serviceConnection)
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
                                createVideo(
                                    path
                                ) { video -> service.upload(video.videoId, path) }
                            }
                        }
                    } ?: run {
                        // Single file
                        data.data?.let { uri ->
                            val path = Utils.getFilePath(this, uri)!!
                            createVideo(
                                path
                            ) { video -> service.upload(video.videoId, path) }
                        }
                    }
                } ?: Log.e(TAG, "No data received")
            }
        }

    /**
     * Create a video id from path
     */
    private fun createVideo(path: String, onVideoCreated: (Video) -> Unit) {
        val videoName = path.split("/").last()
        client.videos().createAsync(VideoCreationPayload().apply { title = videoName },
            object : ApiCallback<Video> {
                override fun onFailure(
                    e: ApiException?,
                    statusCode: Int,
                    responseHeaders: MutableMap<String, MutableList<String>>?
                ) {
                    runOnUiThread {
                        Utils.showAlertDialog(
                            this@MainActivity,
                            getString(R.string.error),
                            getString(R.string.failed_to_create_video, e?.localizedMessage)
                        )
                    }
                }

                override fun onSuccess(
                    result: Video?,
                    statusCode: Int,
                    responseHeaders: MutableMap<String, MutableList<String>>?
                ) {
                    onVideoCreated(result!!)
                }

                override fun onUploadProgress(
                    bytesWritten: Long,
                    contentLength: Long,
                    done: Boolean
                ) {
                    // Nothing to do
                }

                override fun onDownloadProgress(
                    bytesRead: Long,
                    contentLength: Long,
                    done: Boolean
                ) {
                    // Nothing to do
                }

            })
    }

    override fun onUploadStarted(id: String) {
        Log.i(TAG, "Started to upload: $id")

        runOnUiThread {
            binding.progressBar.visibility = View.VISIBLE
            binding.progressBar.progress = 0
            binding.cancel.visibility = View.VISIBLE
        }
    }

    override fun onUploadComplete(id: String, video: Video) {
        Log.i(
            TAG,
            "File has been successfully upload $id: ${video.videoId}"
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