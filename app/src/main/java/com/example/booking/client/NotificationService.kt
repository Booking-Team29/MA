package com.example.booking.client

import com.example.booking.model.Notification.Notification
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface NotificationService {
    @Headers(
        "User-Agent: Mobile-Android",
        "Content-Type: application/json",
    )
    @GET("notification/user")
    fun getNotifications() : Call<List<Notification>>


    @Headers(
        "User-Agent: Mobile-Android",
        "Content-Type: application/json",
    )
    @POST("notification/user/{id}")
    fun markNotificationRead(@Path("id") id: Long) : Call<Void>

}