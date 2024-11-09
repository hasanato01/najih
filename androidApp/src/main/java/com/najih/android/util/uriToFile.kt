package com.najih.android.util
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import java.io.File

fun uriToFile(uri: Uri, context: Context): File? {
    return try {
        val inputStream = context.contentResolver.openInputStream(uri)
        val cursor = context.contentResolver.query(uri, null, null, null, null)

        // Extract the file name from the cursor
        val fileName = cursor?.let {
            if (it.moveToFirst()) {
                val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                it.getString(nameIndex)
            } else {
                "uploaded_file"
            }
        } ?: "uploaded_file"

        // Encode the file name to handle special characters (like apostrophes)
        val encodedFileName = Uri.encode(fileName)

        // Create the file in the cache directory with the encoded filename
        val file = File(context.cacheDir, encodedFileName)

        inputStream?.use { input ->
            file.outputStream().use { output ->
                input.copyTo(output)
            }
        }

        Log.d("ApiClient", "File created: ${file.absolutePath}")
        file
    } catch (e: Exception) {
        Log.e("ApiClient", "Error converting URI to file: ${e.message}", e)
        null
    }
}
