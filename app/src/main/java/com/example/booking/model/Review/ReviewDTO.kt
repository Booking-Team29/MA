package com.example.booking.model.Review

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.time.LocalDate

class ReviewDTO (
    @SerializedName("reviewId") @Expose val reviewID: Long,
    @SerializedName("reviewerEmail") @Expose val reviewerEmail: String,
    @SerializedName("reviewDate") @Expose val reviewDate: LocalDate,
    @SerializedName("description") @Expose val description: String,
    @SerializedName("rating") @Expose val rating: Double,
    @SerializedName("approved") @Expose val approved: Boolean,
    @SerializedName("reservationId") @Expose val reservationId: Long,
    @SerializedName("userId") @Expose val userId: Long,
    @SerializedName("accommodationId") @Expose val accommodationId: Long,
): Serializable, Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString() ?: "",
        LocalDate.parse(parcel.readString()),
        parcel.readString() ?: "",
        parcel.readDouble(),
        parcel.readByte() != 0.toByte(),
        parcel.readLong(),
        parcel.readLong(),
        parcel.readLong()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(reviewID)
        parcel.writeString(reviewerEmail)
        parcel.writeString(description)
        parcel.writeDouble(rating)
        parcel.writeByte(if (approved) 1 else 0)
        parcel.writeLong(reservationId)
        parcel.writeLong(userId)
        parcel.writeLong(accommodationId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ReviewDTO> {
        override fun createFromParcel(parcel: Parcel): ReviewDTO {
            return ReviewDTO(parcel)
        }

        override fun newArray(size: Int): Array<ReviewDTO?> {
            return arrayOfNulls(size)
        }
    }
}