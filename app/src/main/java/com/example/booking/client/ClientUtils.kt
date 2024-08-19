package com.example.booking.client

import com.auth0.jwt.JWT
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ClientUtils {
    const val API_URL: String = "http://192.168.1.105:8080/api/v1/"
    var jwt: String = ""
    var role: String = ""
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(createHttpClient())
        .build()
    val accommodationService: AccommodationService = retrofit.create(AccommodationService::class.java)
    val reviewService: ReviewService = retrofit.create(ReviewService::class.java)
    val userService: UserService = retrofit.create(UserService::class.java)

    private fun createHttpClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(AuthInterceptor())
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    fun setToken(jwt: String) {
        this.jwt = jwt
        this.role = JWT.decode(jwt).getClaim("role").asString()
        System.out.println("Role: " + this.role)
    }

    fun logOut() {
        this.jwt = ""
        this.role = ""
    }
}

class AuthInterceptor() : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val newRequest = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer ${ClientUtils.jwt}")
                .build()
            return chain.proceed(newRequest)
        }
}