package com.tresor.common.model.viewmodel

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by kris on 4/16/18. Tokopedia
 */
data class HashtagListModel(val id : String,
                            val hashtag : String,
                            val amountUnformatted : Double,
                            val amount : String) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readDouble(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(hashtag)
        parcel.writeDouble(amountUnformatted)
        parcel.writeString(amount)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<HashtagListModel> {
        override fun createFromParcel(parcel: Parcel): HashtagListModel {
            return HashtagListModel(parcel)
        }

        override fun newArray(size: Int): Array<HashtagListModel?> {
            return arrayOfNulls(size)
        }
    }
}