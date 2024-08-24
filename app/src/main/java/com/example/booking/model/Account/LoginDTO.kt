package com.example.booking.model.Account

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class LoginDTO (
    @SerializedName("username") @Expose var username: String,
    @SerializedName("password") @Expose var password: String
) : Serializable, Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(username)
        parcel.writeString(password)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LoginDTO> {
        override fun createFromParcel(parcel: Parcel): LoginDTO {
            return LoginDTO(parcel)
        }

        override fun newArray(size: Int): Array<LoginDTO?> {
            return arrayOfNulls(size)
        }
    }
}