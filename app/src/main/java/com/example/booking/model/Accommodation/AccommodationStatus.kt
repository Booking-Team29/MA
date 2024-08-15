package com.example.booking.model.Accommodation

import com.google.gson.annotations.SerializedName

enum class AccommodationStatus {
    @SerializedName("CREATED")
    CREATED,
    @SerializedName("APPROVED")
    APPROVED,
    @SerializedName("DENIED")
    DENIED,
    @SerializedName("EDITED")
    EDITED
}