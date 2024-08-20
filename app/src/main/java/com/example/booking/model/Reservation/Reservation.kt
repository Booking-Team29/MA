package com.example.booking.model.Reservation

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Reservation (
    @SerializedName("id") @Expose var id: Long,
    @SerializedName("startDate") @Expose var startDate: String,
    @SerializedName("endDate") @Expose var endDate: String,
    @SerializedName("guestsCount") @Expose var guestsCount: Int,
    @SerializedName("status") @Expose var reservationStatus: ReservationStatus,
    @SerializedName("totalPrice") @Expose var totalPrice: Int,
    @SerializedName("userId") @Expose var userId: Long,
    @SerializedName("accommodationId") @Expose var accommodationId: Long,
) : Serializable, Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        ReservationStatus.valueOf(parcel.readString().toString()),
        parcel.readInt(),
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
        parcel.writeLong(userId)
        parcel.writeLong(accommodationId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Reservation> {
        override fun createFromParcel(parcel: Parcel): Reservation {
            return Reservation(parcel)
        }

        override fun newArray(size: Int): Array<Reservation?> {
            return arrayOfNulls(size)
        }
    }
}