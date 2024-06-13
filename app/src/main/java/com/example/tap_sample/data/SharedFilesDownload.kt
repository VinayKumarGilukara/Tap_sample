package com.example.tap_sample.data

import android.content.Context
import android.os.Environment
import java.io.File

class SharedFilesDownload {
    fun getSharedFilePath(context: Context?): String? {

        val filePath = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
                .toString() + File.separator + "MyFolder/"
        )
        filePath.mkdirs()
        return filePath.path
    }
}