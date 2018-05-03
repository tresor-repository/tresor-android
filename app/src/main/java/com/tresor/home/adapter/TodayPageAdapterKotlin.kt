package com.tresor.home.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.tresor.R
import com.tresor.common.model.viewmodel.SpendingModel
import com.tresor.home.viewholder.TodayPageAdapterViewHolder

/**
 * Created by kris on 5/2/18. Tokopedia
 */
class TodayPageAdapterKotlin(private val spendingModels: MutableList<SpendingModel>,
                             private val listener: TodayPageAdapterViewHolder
                             .TodaySpendingAdapterListener):
        RecyclerView.Adapter<TodayPageAdapterViewHolder>() {

    override fun onBindViewHolder(holder: TodayPageAdapterViewHolder, position: Int) {
        holder.bind(spendingModels)
    }

    override fun getItemCount(): Int {
        return 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodayPageAdapterViewHolder {
        val itemView = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.today_page_adapter_view_holder, parent, false)
        return TodayPageAdapterViewHolder(itemView, listener)
    }

    fun updateData(spendingModels: MutableList<SpendingModel>) {
        spendingModels.clear()
        spendingModels.addAll(spendingModels)
    }
}