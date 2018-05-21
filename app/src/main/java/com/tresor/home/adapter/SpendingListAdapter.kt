package com.tresor.home.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tresor.R
import com.tresor.common.model.viewmodel.SpendingModel
import com.tresor.common.viewholder.ItemAdapterViewHolder
import com.tresor.home.viewholder.SpendingListItemViewHolder
import com.tresor.home.viewholder.SpendingListHeaderViewHolder

/**
 * Created by kris on 5/2/18. Tokopedia
 */
class SpendingListAdapter(private val spendingModels: MutableList<SpendingModel>,
                          private val listener:
                             SpendingListItemViewHolder.SpendingItemListener)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>(), ItemAdapterViewHolder.HeaderListener {

    override fun recalculateAfterDelete() {
        notifyItemChanged(0)
        when(spendingModels.size) {
            0 -> listener.onItemEmpty()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        when (holder) {
            is SpendingListHeaderViewHolder -> holder.bindHeader(spendingModels)
            else -> (holder as SpendingListItemViewHolder).bind(spendingModels)
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
            0 -> SpendingListHeaderViewHolder(
                    inflateItemView(parent, R.layout.today_header_list_adapter),
                    listener
            )
            else -> SpendingListItemViewHolder(
                    inflateItemView(parent, R.layout.today_page_adapter_view_holder),
                    listener, this)
        }
    }

    private fun inflateItemView(parent: ViewGroup, layout: Int): View {
        return LayoutInflater.from(parent.context).inflate(layout, parent, false)
    }

    fun updateFilteredData(spendingModels: MutableList<SpendingModel>) {
        this::spendingModels.get().clear()
        this::spendingModels.get().addAll(spendingModels)
        notifyDataSetChanged()
    }


}