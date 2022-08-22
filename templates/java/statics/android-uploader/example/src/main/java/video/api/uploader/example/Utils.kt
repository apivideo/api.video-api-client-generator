package video.api.uploader.example

import android.content.Context
import android.content.DialogInterface
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import androidx.appcompat.app.AlertDialog

object Utils {
    /**
     * Get file path from URI.
     * We should use [MediaStore.Files.FileColumns.RELATIVE_PATH] and [MediaStore.Files.FileColumns.DISPLAY_NAME]
     * but the referenced issue say DATA is fine (even if it is deprecated...)>
     * @see https://issuetracker.google.com/issues/151407044
     */
    fun getFilePath(context: Context, uri: Uri): String? {
        var cursor: Cursor? = null
        val projection = arrayOf(MediaStore.Files.FileColumns.DATA)
        try {
            cursor = context.contentResolver.query(
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

    fun showAlertDialog(
        context: Context,
        title: String,
        message: String = "",
        afterPositiveButton: () -> Unit = {}
    ) {
        AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(android.R.string.ok) { dialogInterface: DialogInterface, _: Int ->
                dialogInterface.dismiss()
                afterPositiveButton()
            }
            .show()
    }
}