package com.example.booking.client

import com.example.booking.model.Accommodation.AccommodationFilterDTO
import com.example.booking.model.Reservation.Reservation
import com.example.booking.model.Reservation.ReservationRequest
import com.example.booking.model.Reservation.ReservationRequestDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query
import java.time.LocalDate

interface ReservationService {
    @Headers(
        "User-Agent: Mobile-Android",
        "Content-Type: application/json",
    )
    @POST("reservation")
    fun makeReservation(@Body reservationRequestDTO: ReservationRequestDTO) : Call<Void>

    @Headers(
        "User-Agent: Mobile-Android",
        "Content-Type: application/json",
    )
    @GET("reservation")
    fun getReservations() : Call<List<Reservation>>

    @Headers(
        "User-Agent: Mobile-Android",
        "Content-Type: application/json",
    )
    @GET("reservation/reservationRequests")
    fun getReservationRequests() : Call<List<ReservationRequest>>
}