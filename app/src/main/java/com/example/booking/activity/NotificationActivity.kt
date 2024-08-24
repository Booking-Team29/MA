package com.example.booking.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.booking.adapter.NotificationAdapter
import com.example.booking.client.ClientUtils
import com.example.booking.databinding.ActivityNotificationBinding
import com.example.booking.model.Notification.Notification
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate

class NotificationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNotificationBinding
    private var notifications: List<Notification> = emptyList()
    private lateinit var adapter: NotificationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = NotificationAdapter(this, emptyList())
        binding.notificaitonListView.adapter = adapter

        getNotifications()
    }

    private fun getNotifications() {
        val call =  ClientUtils.notificationService.getNotifications()
        call.enqueue(object : Callback<List<Notification>> {
            override fun onResponse(call: Call<List<Notification>>, response: Response<List<Notification>>) {
                if (response.isSuccessful) {
                    val notis = response.body()
                    if (notis != null) {
                        notifications = notis
                        notifications = notifications.sortedByDescending { LocalDate.parse(it.CreationTime) }
                        adapter.updateItems(notifications)
                    } else {
                        Toast.makeText(this@NotificationActivity, "Server failed to return anything", Toast.LENGTH_SHORT).show()
                    }

                } else Toast.makeText(this@NotificationActivity, "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<List<Notification>>, t: Throwable) {
                System.out.println("Error getting notifications: " + t)
                Toast.makeText(this@NotificationActivity, "Failed to fetch notifications", Toast.LENGTH_SHORT).show()
            }
        })
    }
}