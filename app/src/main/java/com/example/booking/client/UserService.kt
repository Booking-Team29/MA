package com.example.booking.client

import com.example.booking.model.Account.LoginDTO
import com.example.booking.model.Account.TokenDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface UserService {
    @Headers(
        "User-Agent: Mobile-Android",
        "Content-Type: application/json",
    )
    @POST("account/login")
    fun login(@Body loginDTO: LoginDTO) : Call<TokenDTO>
}