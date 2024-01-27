package com.example.adminproagro.model

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

class OrderDetails() : Serializable {
    var userEmail: String? = null
    var userName: String? = null

    var productNames: MutableList<String>? = null
    var productImages: MutableList<String>? = null
    var productPrices: MutableList<String>? = null
    var productQuantities: MutableList<Int>? = null

    var address: String? = null
    var totalPrice: String? = null
    var phoneNumber: String? = null

    var orderAccepted: Boolean = false
    var paymentReceived: Boolean = false
    var orderReceived: Boolean = false

    var itemPushKey: String? = null

    var currentTime: Long = 0

    constructor(parcel: Parcel) : this() {
        userEmail = parcel.readString()
        userName = parcel.readString()
        address = parcel.readString()
        totalPrice = parcel.readString()
        phoneNumber = parcel.readString()
        orderAccepted = parcel.readByte() != 0.toByte()
        paymentReceived = parcel.readByte() != 0.toByte()
        orderReceived = parcel.readByte() != 0.toByte()
        itemPushKey = parcel.readString()
        currentTime = parcel.readLong()
    }

     fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(userEmail)
        parcel.writeString(userName)
        parcel.writeString(address)
        parcel.writeString(totalPrice)
        parcel.writeString(phoneNumber)
        parcel.writeByte(if (orderAccepted) 1 else 0)
        parcel.writeByte(if (paymentReceived) 1 else 0)
        parcel.writeByte(if (orderReceived) 1 else 0)
        parcel.writeString(itemPushKey)
        parcel.writeLong(currentTime)
    }

     fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<OrderDetails> {
        override fun createFromParcel(parcel: Parcel): OrderDetails {
            return OrderDetails(parcel)
        }

        override fun newArray(size: Int): Array<OrderDetails?> {
            return arrayOfNulls(size)
        }
    }
}