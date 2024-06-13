package com.example.tap_sample.database


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException


@Database(entities =[Inspector::class,LineRoute::class,HotSheet::class,ServiceType::class,Notifications::class] , version = 1, exportSchema = false)
abstract class RoomDB:RoomDatabase() {
    abstract fun inspectorDao(): InspectorDao
    abstract fun lineRouteDao():LineRouteDao
    abstract fun hotSheetDao():HotSheetDao
    abstract fun serviceTypeDao():ServiceTypeDao
    abstract fun notificationDao():NotificationDao
   /* companion object {
        private const val DATABASE_NAME = "ecitation.db"

        @Volatile
        private var instance: RoomDB? = null

        fun getInstance(context: Context): RoomDB {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): RoomDB {
            return Room.databaseBuilder(context, RoomDB::class.java, DATABASE_NAME)
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        copyDatabaseFromExternalPath(context)
                    }
                })
                .build()
        }

        private fun copyDatabaseFromExternalPath(context: Context) {
            GlobalScope.launch(Dispatchers.IO) {
                try {
                    val sourcePath = "/data/data/${context.packageName}/databases/$DATABASE_NAME"
                    val destinationPath = context.getDatabasePath(DATABASE_NAME).absolutePath

                    copyFile(File(sourcePath), File(destinationPath))
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }

        @Throws(IOException::class)
        private fun copyFile(sourceFile: File, destFile: File) {
            val sourceChannel = FileInputStream(sourceFile).channel
            val destChannel = FileOutputStream(destFile).channel
            try {
                sourceChannel.transferTo(0, sourceChannel.size(), destChannel)
            } finally {
                sourceChannel.close()
                destChannel.close()
            }
        }
    }*/

}