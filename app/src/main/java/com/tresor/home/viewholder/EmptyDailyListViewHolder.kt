package com.tresor.home.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.empty_daily_list.view.*

/**
 * Created by kris on 5/9/18. Tokopedia
 */
class EmptyDailyListViewHolder(itemView: View, val listener: EmptyDailyListListener)
    : RecyclerView.ViewHolder(itemView) {

    fun bind() = with(itemView) {
        add_spending_no_list_button.setOnClickListener { listener.onAddFirstSpending() }
    }

    interface EmptyDailyListListener{

        fun onAddFirstSpending()

    }

}