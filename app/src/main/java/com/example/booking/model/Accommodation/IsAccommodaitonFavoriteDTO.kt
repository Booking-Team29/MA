package com.example.booking.model.Accommodation

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class IsAccommodaitonFavoriteDTO (
    @SerializedName("isFavorite") @Expose var isFavorite: Boolean
): Serializable, Parcelable{
    constructor(parcel: Parcel) : this(parcel.readByte() != 0.toByte()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeByte(if (isFavorite) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<IsAccommodaitonFavoriteDTO> {
        override fun createFromParcel(parcel: Parcel): IsAccommodaitonFavoriteDTO {
            return IsAccommodaitonFavoriteDTO(parcel)
        }

        override fun newArray(size: Int): Array<IsAccommodaitonFavoriteDTO?> {
            return arrayOfNulls(size)
        }
    }
}