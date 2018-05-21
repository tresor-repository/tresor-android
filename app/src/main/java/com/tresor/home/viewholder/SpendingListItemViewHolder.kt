package com.tresor.home.viewholder

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.tresor.common.adapter.ItemAdapterKotlin
import com.tresor.common.model.viewmodel.SpendingModel
import com.tresor.common.viewholder.ItemAdapterViewHolder
import kotlinx.android.synthetic.main.today_page_adapter_view_holder.view.*

/**
 * Created by kris on 5/3/18. Tokopedia
 */
class SpendingListItemViewHolder(itemView: View,
                                 val listener: SpendingItemListener,
                                 private val headerListener: ItemAdapterViewHolder.HeaderListener)
    : RecyclerView.ViewHolder(itemView) {

    fun bind(spendingModelList: MutableList<SpendingModel>) = with(itemView) {

        item_recycler_view.layoutManager = LinearLayoutManager(context)
        item_recycler_view.adapter = ItemAdapterKotlin(spendingModelList, listener, headerListener)
        item_recycler_view.isNestedScrollingEnabled = false
    }

    interface SpendingItemListener {
        fun onItemClicked(position: Int, spendingModel: SpendingModel)
        fun onItemEmpty()
        fun onHeaderClicked()
    }

}