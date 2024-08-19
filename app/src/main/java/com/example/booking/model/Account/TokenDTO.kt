package com.example.booking.model.Account

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class TokenDTO (
    @SerializedName("jwt") @Expose var jwt: String,
    @SerializedName("validFor") @Expose var validFor: Long,
) : Serializable, Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readLong()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(jwt)
        parcel.writeLong(validFor)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TokenDTO> {
        override fun createFromParcel(parcel: Parcel): TokenDTO {
            return TokenDTO(parcel)
        }

        override fun newArray(size: Int): Array<TokenDTO?> {
            return arrayOfNulls(size)
        }
    }
}