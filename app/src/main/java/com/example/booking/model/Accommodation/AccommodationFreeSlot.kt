package com.example.booking.model.Accommodation

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.time.LocalDate

class AccommodationFreeSlot (
    @SerializedName("id") @Expose val ID: Long,
    @SerializedName("startDate") @Expose val startDate: String,
    @SerializedName("endDate") @Expose val endDate: String,
    @SerializedName("accommodationId") @Expose val accommodationId: Long,
    @SerializedName("available") @Expose val available: Boolean,
): Serializable, Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readLong(),
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(ID)
        parcel.writeLong(accommodationId)
        parcel.writeByte(if (available) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AccommodationFreeSlot> {
        override fun createFromParcel(parcel: Parcel): AccommodationFreeSlot {
            return AccommodationFreeSlot(parcel)
        }

        override fun newArray(size: Int): Array<AccommodationFreeSlot?> {
            return arrayOfNulls(size)
        }
    }
}