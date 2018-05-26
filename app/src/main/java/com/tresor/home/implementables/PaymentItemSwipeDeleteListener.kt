package com.tresor.home.implementables

import android.content.Context
import android.support.v7.widget.RecyclerView
import com.tresor.common.utils.SwipeToDeleteCallback
import com.tresor.home.adapter.SpendingListAdapter

/**
 * Created by kris on 5/21/18. Tokopedia
 */
class PaymentItemSwipeDeleteListener(context: Context,
                                     private val spendingListAdapter: SpendingListAdapter)
    : SwipeToDeleteCallback(context) {
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int) {
        when {
            viewHolder!!.adapterPosition != 0 ->
                spendingListAdapter.onDelete(
                        viewHolder.adapterPosition,
                        spendingListAdapter
                                .returnSpendingModelByPosition(viewHolder.adapterPosition))
        }

    }
}