package com.example.booking.client

import com.example.booking.model.Accommodation.AccommodationFilterDTO
import com.example.booking.model.Review.RatingDTO
import com.example.booking.model.Review.ReviewDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface ReviewService {
    @Headers(
        "User-Agent: Mobile-Android",
        "Content-Type: application/json",
    )
    @GET("review/rating/{id}")
    fun getAccommodationRating(
        @Path("id") id: String,
    ) : Call<RatingDTO>

    @GET("review/accommodation/{id}")
    fun getAccommodationReview(
        @Path("id") id: String,
    ) : Call<List<ReviewDTO>>
}