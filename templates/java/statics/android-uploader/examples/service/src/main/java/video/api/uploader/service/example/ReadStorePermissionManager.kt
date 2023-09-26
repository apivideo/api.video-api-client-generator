package video.api.uploader.service.example

import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

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
        get() = ContextCompat.checkSelfPermission(
            activity,
            requiredPermission
        ) == PackageManager.PERMISSION_GRANTED

    fun requestPermission() {
        if (hasPermission) {
            onGranted()
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, requiredPermission)) {
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
