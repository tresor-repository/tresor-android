package com.tresor.statistic.totalspending.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater

import android.view.ViewGroup
import com.tresor.R
import com.tresor.common.model.viewmodel.DailySpendingModel
import com.tresor.statistic.totalspending.viewholder.TopSpendingViewHolder

/**
 * Created by kris on 4/24/18. Tokopedia
 */
class TopSpendingAdapter(
        private val originalSpendingModels: MutableList<DailySpendingModel>) :
        RecyclerView.Adapter<TopSpendingViewHolder>() {

    public fun onHighestSpendingClicked() {
        fun selector(spending: DailySpendingModel): Float = spending.totalAmount
        spendingModels = originalSpendingModels.sortedByDescending { selector(it) }.toMutableList()
        notifyDataSetChanged()
    }

    public fun onLowestSpendingClicked() {
        fun selector(spending: DailySpendingModel): Float = spending.totalAmount
        spendingModels = originalSpendingModels.sortedBy { selector(it) }.toMutableList()
        notifyDataSetChanged()
    }

    public fun onSortByDateClicked() = with(spendingModels) {
        clear()
        addAll(originalSpendingModels)
        notifyDataSetChanged()
    }

    private var spendingModels: MutableList<DailySpendingModel>

    init {
        fun selector(spending: DailySpendingModel): Float = spending.totalAmount
        spendingModels = originalSpendingModels.sortedByDescending { selector(it) }.toMutableList()
    }

    override fun getItemCount(): Int {
        return spendingModels.size
    }

    override fun onBindViewHolder(holder: TopSpendingViewHolder, position: Int) {
        holder.bind(spendingModels[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopSpendingViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.top_spending_adapter, parent, false)
        return TopSpendingViewHolder(itemView)
    }

}