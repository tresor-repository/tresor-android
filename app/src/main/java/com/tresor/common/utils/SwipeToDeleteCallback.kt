package com.tresor.common.utils

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import com.tresor.home.viewholder.SpendingListHeaderViewHolder

/**
 * Created by kris on 5/21/18. Tokopedia
 */
abstract class SwipeToDeleteCallback(context: Context): ItemTouchHelper.SimpleCallback(
        0,
        ItemTouchHelper.RIGHT) {

    override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, target: RecyclerView.ViewHolder?): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int) {

    }

    override fun getSwipeDirs(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?): Int {
        when(viewHolder is SpendingListHeaderViewHolder) {
            true -> return 0
            else -> return super.getSwipeDirs(recyclerView, viewHolder)
        }

    }


}