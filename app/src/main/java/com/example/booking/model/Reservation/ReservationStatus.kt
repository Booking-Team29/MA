package com.example.booking.model.Reservation

import com.google.gson.annotations.SerializedName

enum class ReservationStatus {
    @SerializedName("REQUESTED")
    REQUESTED,

    @SerializedName("APPROVED")
    APPROVED,

    @SerializedName("DENIED")
    DENIED,

    @SerializedName("ACTIVE")
    ACTIVE,

    @SerializedName("COMPLETED")
    COMPLETED,

    @SerializedName("DELETED")
    DELETED,

    @SerializedName("CANCELLED")
    CANCELLED
}