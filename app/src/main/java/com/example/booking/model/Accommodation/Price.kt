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
    @SerializedName("start") @Expose val Start: String,
    @SerializedName("end") @Expose val End: String,
) : Serializable, Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString().toString(),
        parcel.readDouble(),
        parcel.readString().toString(),
        parcel.readString().toString()
    ) {
    }
    fun getStartDate(): LocalDate? {
        try {
            LocalDate.parse(Start)
        } catch (e: Exception) {
            return null
        }
        return null
    }

    fun getEndDate(): LocalDate? {
        try {
            LocalDate.parse(End)
        } catch (e: Exception) {
            return null
        }
        return null
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