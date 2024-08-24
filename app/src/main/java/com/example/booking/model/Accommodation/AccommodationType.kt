package com.example.booking.model.Accommodation

import com.google.gson.annotations.SerializedName

enum class AccommodationType {
    @SerializedName("STUDIO")
    STUDIO,
    @SerializedName("APARTMENT")
    APARTMENT,
    @SerializedName("HOTEL")
    HOTEL
}