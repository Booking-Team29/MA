package com.example.booking.model.Notification

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Notification (
    @SerializedName("notificationId") @Expose val NotificationId: Long,
    @SerializedName("content") @Expose val Content: String,
    @SerializedName("creationTime") @Expose val CreationTime: String,
    @SerializedName("read") @Expose val Read: Boolean,
    @SerializedName("userId") @Expose val UserId: Long,
) : Serializable, Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readByte() != 0.toByte(),
        parcel.readLong()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(NotificationId)
        parcel.writeString(Content)
        parcel.writeString(CreationTime)
        parcel.writeByte(if (Read) 1 else 0)
        parcel.writeLong(UserId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Notification> {
        override fun createFromParcel(parcel: Parcel): Notification {
            return Notification(parcel)
        }

        override fun newArray(size: Int): Array<Notification?> {
            return arrayOfNulls(size)
        }
    }
}