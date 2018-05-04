package com.tresor.home.viewholder

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.tresor.common.adapter.ItemAdapterKotlin
import com.tresor.common.model.viewmodel.SpendingModel
import kotlinx.android.synthetic.main.today_page_adapter_view_holder.view.*
import kotlinx.android.synthetic.main.today_header_list_adapter.view.*

/**
 * Created by kris on 5/3/18. Tokopedia
 */
class TodayPageAdapterViewHolder(itemView: View, val listener: TodaySpendingAdapterListener)
    : RecyclerView.ViewHolder(itemView) {

    fun bind(spendingModelList: MutableList<SpendingModel>) = with(itemView) {

        total_expense.text = spendingModelList.map { it.amountUnformatted }.sum().toString()
        add_spending_button.setOnClickListener { listener.onHeaderClicked() }

        item_recycler_view.layoutManager = LinearLayoutManager(context)
        item_recycler_view.adapter = ItemAdapterKotlin(spendingModelList, listener)
    }

    interface TodaySpendingAdapterListener {
        fun onItemClicked(position: Int, spendingModel: SpendingModel)
        fun onHeaderClicked()
    }

}