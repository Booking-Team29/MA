package com.example.booking.model.Reservation

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ReservationRequestDTO (
    @SerializedName("id") @Expose var id: Long,
    @SerializedName("startDate") @Expose var startDate: String,
    @SerializedName("endDate") @Expose var endDate: String,
    @SerializedName("guestsCount") @Expose var guestsCount: Int,
    @SerializedName("totalPrice") @Expose var totalPrice: Int,
    @SerializedName("userEmail") @Expose var userEmail: String,
    @SerializedName("accommodationId") @Expose var accommodationId: Long,
    @SerializedName("slotId") @Expose var slotId: Long,
): Serializable, Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readLong(),
        parcel.readLong()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(startDate)
        parcel.writeString(endDate)
        parcel.writeInt(guestsCount)
        parcel.writeInt(totalPrice)
        parcel.writeString(userEmail)
        parcel.writeLong(accommodationId)
        parcel.writeLong(slotId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ReservationRequestDTO> {
        override fun createFromParcel(parcel: Parcel): ReservationRequestDTO {
            return ReservationRequestDTO(parcel)
        }

        override fun newArray(size: Int): Array<ReservationRequestDTO?> {
            return arrayOfNulls(size)
        }
    }
}