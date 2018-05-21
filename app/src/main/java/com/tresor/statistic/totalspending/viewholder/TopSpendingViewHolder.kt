package com.tresor.statistic.totalspending.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import com.tresor.common.model.viewmodel.DailySpendingModel

import kotlinx.android.synthetic.main.top_spending_adapter.view.*


/**
 * Created by kris on 4/24/18. Tokopedia
 */
class TopSpendingViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
    fun bind(dailySpendingModel: DailySpendingModel) = with(itemView) {
        amountSpent.text = dailySpendingModel.totalAmount.toString()
        spendingDate.text = dailySpendingModel.date
    }
}