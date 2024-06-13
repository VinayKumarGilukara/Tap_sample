package com.example.tap_sample.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tap_sample.R
import com.example.tap_sample.database.Notifications
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

class NotificationsAdapter(private val context: Context, private val notifications: List<Notifications>) :
    RecyclerView.Adapter<NotificationsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.notification_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val notification = notifications[position]
        holder.title.text = notification.title
        holder.message.text = notification.body
        holder.messageTime.text = notification.time

    }

    //get the current time zone
   /* fun getCurrentTimeZone(): String? {
        val tz = Calendar.getInstance().timeZone
        return tz.id
    }


    fun getNewDate(dateString: String?): String? {
        if (dateString == null) {
            return ""
        }
        try {
            val utcFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            utcFormat.timeZone = TimeZone.getTimeZone("UTC")
            val date = utcFormat.parse(dateString)
            val pstFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            pstFormat.timeZone = TimeZone.getTimeZone(getCurrentTimeZone())
            return pstFormat.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return "empty"
    }*/

    private fun getFormattedDate(dateString: String?): String {
        if (dateString.isNullOrEmpty()) {
            return ""
        }

        val utcFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        utcFormat.timeZone = TimeZone.getTimeZone("UTC")

        val pstFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        pstFormat.timeZone = TimeZone.getDefault()

        try {
            val date = utcFormat.parse(dateString)
            return pstFormat.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return ""
    }

    override fun getItemCount(): Int {
        return notifications.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title :TextView = itemView.findViewById(R.id.txt_title)
        val message: TextView = itemView.findViewById(R.id.txt_message)
        val messageTime: TextView = itemView.findViewById(R.id.txt_message_time)
    }
}
