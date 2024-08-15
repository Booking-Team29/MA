package com.example.booking.client

import retrofit2.Call
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import java.time.LocalDate

public interface AccommodationService {
    @Headers(
            "User-Agent: Mobile-Android",
            "Content-Type: application/json",
    )
    @GET("accommodation/accommodationSearch")
    fun searchAcommodations(
        @Query("location") location: String,
        @Query("peopleNumber") peopleNumber: Int,
        @Query("start") start: LocalDate,
        @Query("end") end: LocalDate
        ) : Call<List<AccommodationService>>
}