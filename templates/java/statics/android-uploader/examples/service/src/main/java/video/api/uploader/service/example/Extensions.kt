package video.api.uploader.service.example

import android.content.Context
import android.content.DialogInterface
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog

/**s
 * Get file path from URI.
 * We should use [MediaStore.Files.FileColumns.RELATIVE_PATH] and [MediaStore.Files.FileColumns.DISPLAY_NAME]
 * but the referenced issue say DATA is fine (even if it is deprecated...)>
 * @see https://issuetracker.google.com/issues/151407044
 */
fun Context.getFilePath(uri: Uri): String? {
    var cursor: Cursor? = null
    val projection = arrayOf(MediaStore.Files.FileColumns.DATA)
    try {
        cursor = contentResolver.query(
            uri, projection, null, null,
            null
        )
        if (cursor != null && cursor.moveToFirst()) {
            val index = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA)
            return cursor.getString(index)
        }
    } finally {
        cursor?.close()
    }
    return null
}

fun Context.showDialog(
    title: String,
    message: String = "",
    @StringRes
    positiveButtonText: Int = android.R.string.ok,
    @StringRes
    negativeButtonText: Int = 0,
    onPositiveButtonClick: () -> Unit = {},
    onNegativeButtonClick: () -> Unit = {}
) {
    AlertDialog.Builder(this)
        .setTitle(title)
        .setMessage(message)
        .apply {
            if (positiveButtonText != 0) {
                setPositiveButton(positiveButtonText) { dialogInterface: DialogInterface, _: Int ->
                    dialogInterface.dismiss()
                    onPositiveButtonClick()
                }
            }
            if (negativeButtonText != 0) {
                setNegativeButton(negativeButtonText) { dialogInterface: DialogInterface, _: Int ->
                    dialogInterface.dismiss()
                    onNegativeButtonClick()
                }
            }
        }
        .show()
}