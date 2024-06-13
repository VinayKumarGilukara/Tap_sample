package com.example.tap_sample.database


import android.app.Notification
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NotificationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
     suspend fun insertNotification(notification: Notifications)

    @Query("SELECT * FROM notifications")
    fun getAllNotifications(): LiveData<List<Notifications>>


}
