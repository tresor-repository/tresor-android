package com.tresor.home.model

/**
 * Created by kris on 7/6/17. Tokopedia
 */

data class IconModel(val iconImageId: Int,
                     val defaultTag: String,
                     val isAvailable: Boolean,
                     var isChosen: Boolean)
