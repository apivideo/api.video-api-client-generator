package video.api.uploader.work.example

import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts

class ReadStorePermissionManager(
    private val activity: ComponentActivity,
    private val onGranted: () -> Unit,
    private val onShowPermissionRationale: (String, () -> Unit) -> Unit,
    private val onDenied: () -> Unit
) {
    private val requiredPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        android.Manifest.permission.READ_MEDIA_VIDEO
    } else {
        android.Manifest.permission.READ_EXTERNAL_STORAGE
    }

    private val hasPermission: Boolean
        get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.checkSelfPermission(requiredPermission) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }

    fun requestPermission() {
        if (hasPermission) {
            onGranted()
        } else {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                throw IllegalStateException("Permission should be granted")
            }
            if (activity.shouldShowRequestPermissionRationale(requiredPermission)) {
                onShowPermissionRationale(requiredPermission) {
                    requestPermission.launch(requiredPermission)
                }
            } else {
                requestPermission.launch(requiredPermission)
            }
        }
    }

    private val requestPermission =
        activity.registerForActivityResult(ActivityResultContracts.RequestPermission()) { permission ->
            if (permission) {
                onGranted()
            } else {
                onDenied()
            }
        }
}
