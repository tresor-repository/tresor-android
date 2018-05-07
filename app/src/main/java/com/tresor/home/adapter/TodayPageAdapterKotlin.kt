package com.tresor.home.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tresor.R
import com.tresor.common.model.viewmodel.SpendingModel
import com.tresor.common.viewholder.ItemAdapterViewHolder
import com.tresor.home.viewholder.TodayPageAdapterViewHolder
import com.tresor.home.viewholder.TodayPageHeaderViewHolder

/**
 * Created by kris on 5/2/18. Tokopedia
 */
class TodayPageAdapterKotlin(private val spendingModels: MutableList<SpendingModel>,
                             private val listener:
                             TodayPageAdapterViewHolder.TodaySpendingAdapterListener)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>(), ItemAdapterViewHolder.HeaderListener {

    override fun recalculateAfterDelete() {
        notifyItemChanged(0)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        when (holder) {
            is TodayPageHeaderViewHolder -> holder.bindHeader(spendingModels)
            else -> (holder as TodayPageAdapterViewHolder).bind(spendingModels)
        }
    }

    override fun getItemCount(): Int {
        return 2
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewTypeByPosition: Int)
            : RecyclerView.ViewHolder {
        return when (viewTypeByPosition) {
            0 -> TodayPageHeaderViewHolder(
                    inflateItemView(parent, R.layout.today_header_list_adapter),
                    listener
            )
            else -> TodayPageAdapterViewHolder(
                    inflateItemView(parent, R.layout.today_page_adapter_view_holder),
                    listener, this)
        }
    }

    private fun inflateItemView(parent: ViewGroup, layout: Int): View {
        return LayoutInflater.from(parent.context).inflate(layout, parent, false)
    }

    fun updateFilteredData(spendingModels: MutableList<SpendingModel>) {
        spendingModels.clear()
        spendingModels.addAll(spendingModels)
        notifyDataSetChanged()
    }


}