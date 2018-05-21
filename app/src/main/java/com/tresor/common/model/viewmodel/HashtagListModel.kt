package com.tresor.common.model.viewmodel

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by kris on 4/16/18. Tokopedia
 */
data class HashtagListModel(val id : String,
                            val hashtag : String,
                            val amountUnformatted : List<Double>,
                            val amount : List<String>)