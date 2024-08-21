package com.example.booking.model.Accommodation

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class AccommodationChangeConfirmationMethodDTO (
    @SerializedName("ConfirmationMethod") @Expose var status: ConfirmationMethod
) : Serializable, Parcelable {
    constructor(parcel: Parcel) : this(
        ConfirmationMethod.valueOf(parcel.readString().toString()),
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AccommodationChangeConfirmationMethodDTO> {
        override fun createFromParcel(parcel: Parcel): AccommodationChangeConfirmationMethodDTO {
            return AccommodationChangeConfirmationMethodDTO(parcel)
        }

        override fun newArray(size: Int): Array<AccommodationChangeConfirmationMethodDTO?> {
            return arrayOfNulls(size)
        }
    }
}