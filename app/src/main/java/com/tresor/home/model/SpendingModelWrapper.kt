package com.tresor.home.model

import android.os.Parcel
import android.os.Parcelable
import com.tresor.common.model.viewmodel.SpendingModel

/**
 * Created by kris on 5/3/18. Tokopedia
 */
data class SpendingModelWrapper(val adapterPosition: Int,
                                val spendingModel: SpendingModel) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readParcelable(SpendingModel::class.java.classLoader))

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(adapterPosition)
        parcel.writeParcelable(spendingModel, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SpendingModelWrapper> {
        override fun createFromParcel(parcel: Parcel): SpendingModelWrapper {
            return SpendingModelWrapper(parcel)
        }

        override fun newArray(size: Int): Array<SpendingModelWrapper?> {
            return arrayOfNulls(size)
        }
    }

}