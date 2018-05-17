package com.tresor.home.viewholder

import com.tresor.R
import com.tresor.common.model.viewmodel.SpendingModel
import com.tresor.home.adapter.SpendingListAdapter

/**
 * Created by kris on 5/17/18. Tokopedia
 */
class SpendingAdapterDataHelper(val spendingList: MutableList<SpendingModel>) {

    val listOfItems = mutableListOf<Any>()
    private val headerLayoutId = R.layout.today_header_list_adapter
    private val bodyLayoutId = R.layout.financial_list_adapter
    private val firstItemIndex = 1
    private val numberOfHeader = 1

    init {
        listOfItems.add(HEADER)
        listOfItems.addAll(spendingList)
    }

    fun addItem(spendingListAdapter: SpendingListAdapter, spendingModel: SpendingModel) {
        spendingList.add(0,spendingModel)
        listOfItems.add(firstItemIndex, spendingModel)
        spendingListAdapter.notifyItemInserted(firstItemIndex)
        spendingListAdapter.notifyItemRangeChanged(0, listOfItems.size)
    }

    fun editItem(spendingListAdapter: SpendingListAdapter,
                 adapterIndex: Int,
                 spendingModel: SpendingModel) {
        spendingList[adapterIndex - numberOfHeader] = spendingModel
        listOfItems[adapterIndex] = spendingModel
        spendingListAdapter.notifyItemChanged(adapterIndex)
    }

    fun removeItem(spendingListAdapter: SpendingListAdapter,
                   adapterPosition: Int,
                   spendingModel: SpendingModel) {
        spendingList.remove(spendingModel)
        listOfItems.removeAt(adapterPosition)
        spendingListAdapter.notifyItemRemoved(adapterPosition)
        spendingListAdapter.notifyItemRangeChanged(adapterPosition, listOfItems.size)
    }

    fun setViewType(position: Int): Int {
        return when(position) {
            0 -> headerLayoutId
            else -> bodyLayoutId
        }
    }

    fun totalSize(): Int {
        return listOfItems.size
    }

    fun totalSpendingListSize(): Int {
        return spendingList.size
    }

    companion object {
        val HEADER = "HEADER"
    }
}