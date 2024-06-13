package com.example.tap_sample.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
/*
@Entity(tableName = "notifications")
data class Notifications(@PrimaryKey(autoGenerate = true)
                         @ColumnInfo(name = "_id") val id:Int,
                         @ColumnInfo(name = "notification_id") val notificationId:String,
                         @ColumnInfo(name = "message") val messageContent:String,
                         @ColumnInfo(name = "message_time",defaultValue = "CURRENT_TIMESTAMP") val messageTime: String,
                         @ColumnInfo(name = "message_status",defaultValue = "0") val messageStatus:Int) {
}*/

@Entity(tableName = "notifications")
data class Notifications(
    @PrimaryKey(autoGenerate = true)
    val id:Int=0,
    val title:String,
    val body:String,
    val time:String
)