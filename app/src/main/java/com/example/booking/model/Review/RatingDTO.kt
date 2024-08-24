package com.example.booking.model.Review

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class RatingDTO (
    @SerializedName("rating") @Expose val Rating: Double
): Serializable, Parcelable {
    constructor(parcel: Parcel) : this(parcel.readDouble()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeDouble(Rating)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RatingDTO> {
        override fun createFromParcel(parcel: Parcel): RatingDTO {
            return RatingDTO(parcel)
        }

        override fun newArray(size: Int): Array<RatingDTO?> {
            return arrayOfNulls(size)
        }
    }
}