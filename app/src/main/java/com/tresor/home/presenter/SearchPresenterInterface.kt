package com.tresor.home.presenter

import com.tresor.common.adapter.AutoCompleteSuggestionAdapter
import com.tresor.common.model.viewmodel.SpendingModel
import com.tresor.common.widget.template.SmartAutoCompleteTextView

/**
 * Created by kris on 5/13/18. Tokopedia
 */
interface SearchPresenterInterface {

    fun fetchSearchData(startDate: String, endDate: String, hashTagList: List<String>)

    fun updateFilter(startDate: String, endDate: String, hashTagList: List<String>)

    fun autoCompletePresenterListener(startDate: String,
                                      endDate: String,
                                      hashTagSuggestions: MutableList<String>,
                                      arrayAdapter: AutoCompleteSuggestionAdapter): SmartAutoCompleteTextView.AutoCompleteListener

    fun deleteSpending(adapterIndex: Int, spendingModel: SpendingModel)

    fun addNewSpending(spendingModel: SpendingModel)

    fun editNewSpending(position: Int, spendingModel: SpendingModel)

    fun loadMorePage(shownItemSize: Int, currentDataSize: Int)

}