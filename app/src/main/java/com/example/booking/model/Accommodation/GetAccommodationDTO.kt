package com.example.booking.model.Accommodation

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.ArrayList

class GetAccommodationDTO (
    @SerializedName("id") @Expose val ID: Long,
    @SerializedName("name") @Expose val Name: String,
    @SerializedName("description") @Expose val Description: String,
    @SerializedName("location") @Expose val Location: String,
    @SerializedName("locationCoordinates") @Expose val LocationCoordinates: List<Float>,
    @SerializedName("minGuests") @Expose val MinGuests: Int,
    @SerializedName("maxGuests") @Expose val MaxGuests: Int,
    @SerializedName("prices") @Expose val prices: List<Price>,
    @SerializedName("pricingType") @Expose val PricingType: String,
    @SerializedName("daysForCancellation") @Expose val DaysForCancellation: Int,
    @SerializedName("amenities") @Expose val Amenities: ArrayList<String>?,
    @SerializedName("accommodationStatus") @Expose val accommodationStatus: AccommodationStatus,
    @SerializedName("images") @Expose val Images: ArrayList<String>?,
    @SerializedName("type") @Expose val Type: AccommodationType,
    @SerializedName("confirmationMethod") @Expose val confirmationMethod: ConfirmationMethod,
    @SerializedName("slots") @Expose val slots: List<AccommodationFreeSlot>,

): Serializable, Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.createFloatArray()?.toList() ?: emptyList(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.createTypedArrayList(Price)?.toList() ?: emptyList(),
        parcel.readString().toString(),
        parcel.readInt(),
        parcel.createStringArrayList(),
        AccommodationStatus.valueOf(parcel.readString().toString()),
        parcel.createStringArrayList(),
        AccommodationType.valueOf(parcel.readString().toString()),
        ConfirmationMethod.valueOf(parcel.readString().toString()),
        parcel.createTypedArrayList(AccommodationFreeSlot)?.toList() ?: emptyList()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(ID)
        parcel.writeString(Name)
        parcel.writeString(Description)
        parcel.writeString(Location)
        parcel.writeInt(MinGuests)
        parcel.writeInt(MaxGuests)
        parcel.writeTypedList(prices)
        parcel.writeString(PricingType)
        parcel.writeInt(DaysForCancellation)
        parcel.writeTypedList(slots)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GetAccommodationDTO> {
        override fun createFromParcel(parcel: Parcel): GetAccommodationDTO {
            return GetAccommodationDTO(parcel)
        }

        override fun newArray(size: Int): Array<GetAccommodationDTO?> {
            return arrayOfNulls(size)
        }
    }
}