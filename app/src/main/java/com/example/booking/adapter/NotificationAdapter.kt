package com.example.booking.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.compose.ui.graphics.Color
import com.example.booking.R
import com.example.booking.client.ClientUtils
import com.example.booking.model.Notification.Notification
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationAdapter(private val context: Context, private var items: List<Notification>): BaseAdapter() {
    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(position: Int): Any {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.fragment_notification, parent, false)
        val creationTime: TextView = view.findViewById(R.id.notificationCreationTime)
        val contents: TextView = view.findViewById(R.id.notificationContents)
        val markAsRead: Button = view.findViewById(R.id.markAsReadButton)

        val item = items[position]
        creationTime.text = "Creation time: " + item.CreationTime
        contents.text = item.Content

        view.setBackgroundColor(android.graphics.Color.parseColor("#FFCBE8FF"))
        if (!item.Read) {
            markAsRead.visibility = View.VISIBLE
            markAsRead.setOnClickListener { markAsRead(item.NotificationId, markAsRead, view) }
        } else {
            markAsRead.visibility = View.INVISIBLE
            view.setBackgroundColor(android.graphics.Color.WHITE)
        }
        return view
    }

    fun updateItems(newItems: List<Notification>) {
        items = newItems
        notifyDataSetChanged()
    }

    private fun markAsRead(notificaitonId: Long, button: Button, view: View) {
        val call =  ClientUtils.notificationService.markNotificationRead(notificaitonId)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(context, "Notification marked as read", Toast.LENGTH_SHORT).show()
                    button.visibility = View.INVISIBLE
                    view.setBackgroundColor(android.graphics.Color.WHITE)
                }
                else Toast.makeText(context, "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                System.out.println("Error marking notification: " + t)
                Toast.makeText(context, "Failed to fetch notifications", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
