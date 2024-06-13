package com.example.tap_sample.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.example.tap_sample.MainActivity
import com.example.tap_sample.R
import com.example.tap_sample.database.Notifications
import com.example.tap_sample.viewmodels.LoginViewModel
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.sql.Time
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

const val channelId = "notification"
const val channelName ="com.example.tap_sample"

class MyFirebaseMessagingService:FirebaseMessagingService() {
    private val TAG = "Notifications"

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
       if (remoteMessage.notification != null) {
            val title = remoteMessage.notification!!.title!!
            val message = remoteMessage.notification!!.body!!
           val currentTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())

           val notification = Notifications(0,title,message,currentTime)
            Log.v(TAG, " message $notification")

           NotificationLiveData.postValue(notification)

            generateNotification(title, message)
        }

    }

    fun getRemoteView(title:String,message: String):RemoteViews{
        val remoteView = RemoteViews("com.example.tap_sample",R.layout.notification_list_item)
        remoteView.setTextViewText(R.id.txt_title,title)
        remoteView.setTextViewText(R.id.txt_message,message)

        return remoteView
    }

    fun generateNotification(title:String,message:String){
        val intent = Intent(this,MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivity(this,0,intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE)

        var builder : NotificationCompat.Builder = NotificationCompat.Builder(applicationContext,channelId)
            .setSmallIcon(R.drawable.baseline_add_alert_24)
            .setAutoCancel(true)
            .setVibrate(longArrayOf(1000,1000,1000,1000))
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)

        builder = builder.setContent(getRemoteView(title,message))
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            val notificationChannel = NotificationChannel(channelId, channelName,NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
        }
        notificationManager.notify(0,builder.build())

    }
}




