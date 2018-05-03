package com.tresor.common.model.viewmodel

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by kris on 12/30/17. Tokopedia
 */
data class SpendingModel(val id: Int,
                         val amount: String,
                         val amountUnformatted: Double,
                         val userComma: Boolean,
                         val currencyId: Int,
                         val hashTagString: String,
                         val date:String,
                         val theme:Int,
                         val listHashTag: List<String>,
                         val info: String) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readDouble(),
            parcel.readByte() != 0.toByte(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.createStringArrayList(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(amount)
        parcel.writeDouble(amountUnformatted)
        parcel.writeByte(if (userComma) 1 else 0)
        parcel.writeInt(currencyId)
        parcel.writeString(hashTagString)
        parcel.writeString(date)
        parcel.writeInt(theme)
        parcel.writeStringList(listHashTag)
        parcel.writeString(info)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SpendingModel> {
        override fun createFromParcel(parcel: Parcel): SpendingModel {
            return SpendingModel(parcel)
        }

        override fun newArray(size: Int): Array<SpendingModel?> {
            return arrayOfNulls(size)
        }
    }

}