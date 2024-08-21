package com.example.booking.client

import com.example.booking.model.Accommodation.Accommodation
import com.example.booking.model.Accommodation.AccommodationChangeConfirmationMethodDTO
import com.example.booking.model.Accommodation.AccommodationFilterDTO
import com.example.booking.model.Accommodation.GetAccommodationDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PUT
import retrofit2.http.Path
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
        ) : Call<List<AccommodationFilterDTO>>

    @Headers(
        "User-Agent: Mobile-Android",
        "Content-Type: application/json",
    )
    @GET("accommodation/{id}")
    fun getAccommodation(
        @Path("id") id: String,
    ) : Call<GetAccommodationDTO>

    @Headers(
        "User-Agent: Mobile-Android",
        "Content-Type: application/json",
    )
    @GET("accommodation/owner")
    fun getOwnerAccommodation() : Call<List<GetAccommodationDTO>>

    @Headers(
        "User-Agent: Mobile-Android",
        "Content-Type: application/json",
    )
    @PUT("accommodation/define/{id}")
    fun updateAccommodationConfirmationMethod(
        @Path("id") id: String,
        @Body dto: AccommodationChangeConfirmationMethodDTO
    ) : Call<Void>
}