package edu.uw.ischool.mwoode.quizdroid

import android.app.DownloadManager
import android.content.Context
import android.os.Environment
import android.util.Log
import androidx.core.net.toUri
import java.io.File

class AndroidDownloader(
    private val context: Context
): Downloader {

    private val downloadManager = context.getSystemService(DownloadManager::class.java)

    override fun downloadFile(url: String): Long {
        val fileName = "questions.json"
        // val destinationDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        // val destinationFile = File(Environment.getExternalStorageDirectory().getAbsolutePath() + fileName);
        val file = File(
            Environment.getExternalStorageDirectory().toString() + File.separator + Environment.DIRECTORY_DOWNLOADS,
            "/$fileName"
        )

        if (file.exists()) {
            Log.i("INFO", "Exists")
            file.delete()
        }

        val request = DownloadManager.Request(url.toUri())
            .setMimeType("application/json")
            .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setTitle(fileName)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)
        return downloadManager.enqueue(request)
    }
}