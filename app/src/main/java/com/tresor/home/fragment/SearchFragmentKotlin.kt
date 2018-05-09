package com.tresor.home.fragment

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.EditText
import com.tresor.R
import com.tresor.common.adapter.AutoCompleteSuggestionAdapter
import com.tresor.common.adapter.FilterAdapter
import com.tresor.common.fragment.DateRangeFragmentKotlin
import com.tresor.common.model.viewmodel.SpendingModel
import com.tresor.common.widget.template.SmartAutoCompleteTextView
import com.tresor.home.activity.addPaymentActivityIntent
import com.tresor.home.activity.editPaymentActivityIntent
import com.tresor.home.adapter.SpendingListAdapter
import com.tresor.home.inteface.HomeActivityListener
import com.tresor.home.model.SpendingModelWrapper
import com.tresor.home.viewholder.SpendingListItemViewHolder
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_search_hashtag.*
import java.util.ArrayList

/**
 * Created by kris on 5/8/18. Tokopedia
 */
class SearchFragmentKotlin :
        DateRangeFragmentKotlin(),
        SpendingListItemViewHolder.SpendingItemListener,
        FilterAdapter.onFilterItemClicked {

    private val spendingList = mutableListOf<SpendingModel>()
    private val spendingListAdapter = SpendingListAdapter(spendingList, this)

    companion object {
        fun createInstance(): SearchFragmentKotlin {
            return SearchFragmentKotlin()
        }
    }

    override fun onFilterItemRemoved(hashTagList: MutableList<String>) {
        when {
            hashTagList.isEmpty() -> spendingListAdapter.updateFilteredData(spendingList)
            else -> spendingListAdapter.updateFilteredData(selectedFilterResult(hashTagList))
        }
    }

    override fun onItemClicked(position: Int, spendingModel: SpendingModel) {
        val spendingModelWrapper = SpendingModelWrapper(position, spendingModel)
        startActivityForResult(
                activity.editPaymentActivityIntent(spendingModelWrapper),
                HomeActivityListener.EDIT_PAYMENT_REQUEST_CODE
        )
    }

    override fun onHeaderClicked() {
        startActivityForResult(activity.addPaymentActivityIntent(),
                HomeActivityListener.ADD_NEW_PAYMENT_REQUEST_CODE)
    }

    override fun initMainView() {
        spendingList.clear()
        spendingList.addAll(generateSpendingModelList())
        search_recycler_view.adapter = spendingListAdapter
        spendingListAdapter.notifyDataSetChanged()
        search_recycler_view.layoutManager = (LinearLayoutManager(activity))
        val filterAdapter = FilterAdapter(this)
        filter_recycler_view.adapter = filterAdapter
        val arrayAdapter = AutoCompleteSuggestionAdapter(activity)
        val hashTagSuggestions = mutableListOf<String>()
        auto_complete_search_filter.setAdapter(arrayAdapter)
        auto_complete_search_filter.initComponent(
                CompositeDisposable(),
                autoCompleteListener(arrayAdapter, hashTagSuggestions)
        )
        auto_complete_search_filter.setOnItemClickListener { _, _, position, _ ->
            filterItemClicked(hashTagSuggestions, filterAdapter, position)
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_search_hashtag
    }

    private fun filterItemClicked(autoCompleteHashTagList: List<String>,
                                  filterAdapter: FilterAdapter,
                                  position: Int) {
        filterAdapter.addNewHashTag(autoCompleteHashTagList[position])
        spendingListAdapter.updateFilteredData(selectedFilterResult(
                filterAdapter.hashTagShownInAdapter)
        )
        spendingListAdapter.notifyDataSetChanged()
        auto_complete_search_filter.setText("")
        auto_complete_search_filter.requestFocus()
    }

    private fun selectedFilterResult(filteredTagList: List<String>): MutableList<SpendingModel> {
        return spendingList.indices
                .filter { selectFilter(spendingList[it].hashTagString, filteredTagList) }
                .mapTo(ArrayList()) { spendingList[it] }
    }

    private fun selectFilter(hashTagString: String, listOfFilters: List<String>): Boolean {
        return listOfFilters.indices.any {
            hashTagString
                    .toLowerCase()
                    .contains(listOfFilters[it].toLowerCase())
        }
    }

    private fun autoCompleteListener(
            arrayAdapter: AutoCompleteSuggestionAdapter,
            listOfHashTag: MutableList<String>): SmartAutoCompleteTextView.AutoCompleteListener {

        return object : SmartAutoCompleteTextView.AutoCompleteListener {
            override fun finishedTyping(query: String) {
                listOfHashTag.clear()
                listOfHashTag.add("makan")
                listOfHashTag.add("siang")
                listOfHashTag.add("liburan")
                listOfHashTag.add("pup")
                arrayAdapter.updateData(listOfHashTag)
            }

            override fun onTypingError(e: Throwable) {

            }

            override fun onEditTextEmptied() {
                /*financialHistoryListAdapter.updateFilteredData(financialList);
                financialHistoryListAdapter.notifyDataSetChanged();*/
            }

            override fun onEnterKeyPressed() {
                updateAdapter()
            }
        }
    }

    private fun updateAdapter() {
        spendingListAdapter.updateFilteredData(selectedFilterResult(auto_complete_search_filter.separatedString))
        auto_complete_search_filter.requestFocus()
    }

    override fun startDateEditText(): EditText {
        return start_date_field
    }

    override fun endDateEditText(): EditText {
        return end_date_field
    }

    override fun startDateChanged(date: Int, month: Int, year: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun endDateChanged(date: Int, month: Int, year: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun generateSpendingModelList(): MutableList<SpendingModel> {
        val list = ArrayList<SpendingModel>()
        for (i in 0..7) {
            val hashTagList = ArrayList<String>()
            hashTagList.add("#Makan")
            hashTagList.add("#Siang")
            hashTagList.add("#Liburan")
            val spendingModel = SpendingModel(
                    i,
                    "Rp 50.000",
                    50000.0,
                    false,
                    1,
                    "#Makan#Siang#Liburan",
                    "08.32 WIB February 17th 2017",
                    i,
                    hashTagList,
                    "#Liburan #Makan Martabak Telor Mang Udin the Conqueror #Siang siang 3 Paket"
            )
            list.add(spendingModel)
        }
        return list
    }
}