package com.tresor.common.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.tresor.R
import com.tresor.common.model.viewmodel.SpendingModel
import com.tresor.common.viewholder.ItemAdapterViewHolder
import com.tresor.home.viewholder.TodayPageAdapterViewHolder

/**
 * Created by kris on 5/2/18. Tokopedia
 */
class ItemAdapterKotlin(private val spendingList: MutableList<SpendingModel>,
                        private val listener: TodayPageAdapterViewHolder
                        .TodaySpendingAdapterListener)
    : RecyclerView.Adapter<ItemAdapterViewHolder>(), ItemAdapterViewHolder.ItemAdapterListener {

    override fun onEditProduct(position: Int, spendingModel: SpendingModel) {
        listener.onItemClicked(position, spendingModel)
    }

    override fun onDelete(position: Int) {
        spendingList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, spendingList.size)
    }

    override fun onBindViewHolder(holder: ItemAdapterViewHolder, position: Int) {
        holder.bind(spendingList[position], position, this)
    }

    override fun getItemCount(): Int {
        return spendingList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemAdapterViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.financial_list_adapter, parent, false)
        return ItemAdapterViewHolder(itemView)

    }


}