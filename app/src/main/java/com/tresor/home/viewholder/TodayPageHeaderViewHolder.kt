package com.tresor.home.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import com.tresor.common.model.viewmodel.SpendingModel
import kotlinx.android.synthetic.main.today_header_list_adapter.view.*

/**
 * Created by kris on 5/6/18. Tokopedia
 */
class TodayPageHeaderViewHolder(itemView: View,
                                val listener: TodayPageAdapterViewHolder
                                .TodaySpendingAdapterListener)
    : RecyclerView.ViewHolder(itemView) {

    fun bindHeader(spendingModelList: MutableList<SpendingModel>) = with(itemView) {
        total_expense.text = spendingModelList.map { it.amountUnformatted }.sum().toString()
        add_spending_button.setOnClickListener { listener.onHeaderClicked() }
    }

}