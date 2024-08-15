package com.example.booking.model.Accommodation

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.time.LocalDate

class Price (
    @SerializedName("id") @Expose val ID: Long,
    @SerializedName("type") @Expose val Type: String,
    @SerializedName("amount") @Expose val Amount: Double,
    @SerializedName("start") @Expose val Start: LocalDate,
    @SerializedName("end") @Expose val End: LocalDate,
) : Serializable, Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString().toString(),
        parcel.readDouble(),
        TODO("Start"),
        TODO("End")
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(ID)
        parcel.writeString(Type)
        parcel.writeDouble(Amount)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Price> {
        override fun createFromParcel(parcel: Parcel): Price {
            return Price(parcel)
        }

        override fun newArray(size: Int): Array<Price?> {
            return arrayOfNulls(size)
        }
    }
}