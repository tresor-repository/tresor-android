package com.tresor.home.presenter

import com.tresor.common.adapter.AutoCompleteSuggestionAdapter
import com.tresor.common.model.viewmodel.SpendingModel
import com.tresor.common.widget.template.SmartAutoCompleteTextView

/**
 * Created by kris on 5/11/18. Tokopedia
 */
interface TodaySpendingPresenterInterface {

    fun fetchSpendingList()

    fun updateFilter(hashTagList: List<String>)

    fun autoCompletePresenterListener(hashTagSuggestions: MutableList<String>,
                                      arrayAdapter: AutoCompleteSuggestionAdapter): SmartAutoCompleteTextView.AutoCompleteListener

    fun addNewSpending(spendingModel: SpendingModel)

    fun editNewSpending(position: Int, spendingModel: SpendingModel)

    fun deleteSpendingRecord(position: Int, spendingModel: SpendingModel)

    fun loadMorePage(shownItemSize: Int, currentDataSize: Int)

}