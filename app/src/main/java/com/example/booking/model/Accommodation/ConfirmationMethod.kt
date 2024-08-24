package com.example.booking.model.Accommodation

import com.google.gson.annotations.SerializedName

enum class ConfirmationMethod {
    @SerializedName("AUTOMATIC")
    AUTOMATIC,
    @SerializedName("MANUAL")
    MANUAL
}