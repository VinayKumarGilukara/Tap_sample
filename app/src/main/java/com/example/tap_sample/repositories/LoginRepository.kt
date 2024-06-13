package com.example.tap_sample.repositories


import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tap_sample.database.HotSheet
import com.example.tap_sample.database.HotSheetDao
import com.example.tap_sample.database.Inspector
import com.example.tap_sample.database.InspectorDao
import com.example.tap_sample.database.LineRoute
import com.example.tap_sample.database.LineRouteDao
import com.example.tap_sample.database.NotificationDao
import com.example.tap_sample.database.Notifications
import com.example.tap_sample.database.ServiceType
import com.example.tap_sample.database.ServiceTypeDao

import com.example.tap_sample.network.ApiService
import com.example.tap_sample.network.BaseApiResponse
import com.example.tap_sample.network.NetworkResult
import com.example.tap_sample.network.RemoteDataSource
import com.example.tap_sample.network.ResponseModel
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.BufferedInputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream


import javax.inject.Inject

@ActivityRetainedScoped
class LoginRepository @Inject constructor(
    private val apiService: ApiService,
    private val inspectorDao: InspectorDao,
    private val lineRouteDao: LineRouteDao,
    private val hotSheetDao: HotSheetDao,
    private val serviceTypeDao: ServiceTypeDao,
    private val notificationDao: NotificationDao,
    private val context: Context,
    private val remoteDataSource: RemoteDataSource): BaseApiResponse() {

        private val _inspector = MutableLiveData<Inspector?>()
        val inspector: LiveData<Inspector?>
        get() = _inspector

    suspend fun login(username: String,password: String):Inspector?{
        //_inspector.postValue(inspectorDao.getInspectorByUserNamePassword(username,password))
        return inspectorDao.getInspectorByUserNamePassword(username,password)
    }

    val allTopSheets: LiveData<List<HotSheet>> = hotSheetDao.getAllTopSheets()


    suspend fun fetchData(): Flow<NetworkResult<ResponseModel>> {
        return flow<NetworkResult<ResponseModel>> {
            emit(safeApiCall {  remoteDataSource.fetchData() })
        }.flowOn(Dispatchers.IO)
    }

    fun downloadAndExtractZipFile(urlString: String , progressListener: ((Int) -> Unit)?) {
        val url = URL(urlString)
        val connection = url.openConnection() as HttpURLConnection
        connection.connectTimeout = 5000
        connection.readTimeout = 5000

        val responseCode = connection.responseCode
        if (responseCode == HttpURLConnection.HTTP_OK) {
            val contentLength = connection.contentLength
            val inputStream = connection.inputStream

            // Create a FileOutputStream to save the zip file to app storage
            val file = File(getAppStorageDir(), "ecitation.db.zip")
            val outputStream = FileOutputStream(file)

            val buffer = ByteArray(4096)
            var totalBytesRead = 0
            var bytesRead: Int
            while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                outputStream.write(buffer, 0, bytesRead)
                totalBytesRead += bytesRead
                // progressListener?.invoke((totalBytesRead * 100 / contentLength))
            }

            outputStream.close()
            inputStream.close()
            connection.disconnect()

            // Extract the downloaded zip file
            val extractedDir = File(getAppStorageDir(), "extracted")
            extractedDir.mkdirs()
            extractZipFile(file, extractedDir , progressListener)

            // Delete the downloaded zip file after extraction
            file.delete()
        } else {
            throw IOException("Failed to download the zip file. Response code: $responseCode")
        }
    }

    private fun extractZipFile(zipFile: File, extractionDir: File , progressListener: ((Int) -> Unit)?) {
        try {
            val zipInputStream = ZipInputStream(BufferedInputStream(FileInputStream(zipFile)))
            val buffer = ByteArray(1024)

            var entry: ZipEntry? = zipInputStream.nextEntry
            var totalBytesRead = 0
            while (entry != null) {
                val entryFile = File(extractionDir, entry.name)
                if (entry.isDirectory) {
                    entryFile.mkdirs()
                } else {
                    val outputFile = FileOutputStream(entryFile)
                    var bytesRead: Int
                    while (zipInputStream.read(buffer).also { bytesRead = it } != -1) {
                        outputFile.write(buffer, 0, bytesRead)
                        totalBytesRead += bytesRead
                        progressListener?.invoke((totalBytesRead * 100 / zipFile.length()).toInt())
                    }
                    outputFile.close()
                }
                zipInputStream.closeEntry()
                entry = zipInputStream.nextEntry
            }
            zipInputStream.close()
            //progressListener?.invoke(100)
        } catch (e: Exception) {
            throw IOException("Failed to extract the zip file.", e)
        }
    }


    private fun getAppStorageDir(): File {
        return context.filesDir
    }


    fun searchDatabase(): LiveData<List<LineRoute>> {
        return lineRouteDao.searchDatabase()
    }

     fun getLineRouteByDescription(description: String): LineRoute? {
        return lineRouteDao.getLineRouteByDescription(description)
    }


    suspend fun getServiceTypeList(selectedTransitType: Int): List<ServiceType> {
        return serviceTypeDao.getServiceTypeList(selectedTransitType)
    }

    suspend fun insertNotification(notification: Notifications) {
        notificationDao.insertNotification(notification)
    }

    fun getAllNotifications(): LiveData<List<Notifications>> {
        return notificationDao.getAllNotifications()
    }





}