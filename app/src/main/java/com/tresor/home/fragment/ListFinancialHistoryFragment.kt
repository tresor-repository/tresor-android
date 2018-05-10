package com.tresor.home.fragment

import android.app.Activity
import android.app.Fragment
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.tresor.R
import com.tresor.common.adapter.AutoCompleteSuggestionAdapter
import com.tresor.common.adapter.FilterAdapter
import com.tresor.common.model.viewmodel.SpendingModel
import com.tresor.common.widget.template.SmartAutoCompleteTextView
import com.tresor.home.activity.addPaymentActivityIntent
import com.tresor.home.activity.editPaymentActivityIntent
import com.tresor.home.adapter.EmptyDailyListAdapter
import com.tresor.home.adapter.SpendingListAdapter
import com.tresor.home.inteface.HomeActivityListener
import com.tresor.home.inteface.HomeActivityListener.*
import com.tresor.home.model.SpendingModelWrapper
import com.tresor.home.viewholder.EmptyDailyListViewHolder
import com.tresor.home.viewholder.SpendingListItemViewHolder

import java.util.ArrayList

import io.reactivex.disposables.CompositeDisposable

import kotlinx.android.synthetic.main.fragment_list_financial_history.*

/**
 * Created by kris on 5/27/17. Tokopedia
 */

class ListFinancialHistoryFragment : Fragment(),
        SpendingListItemViewHolder.SpendingItemListener,
        EmptyDailyListViewHolder.EmptyDailyListListener,
        FilterAdapter.onFilterItemClicked {

    private val spendingList: MutableList<SpendingModel> = mutableListOf()
    private val spendingListAdapter: SpendingListAdapter =
            SpendingListAdapter(spendingList, this)
    private val emptyAdapter = EmptyDailyListAdapter(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_list_financial_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAutoCompleteView()
        setSpendingList(spendingModelList())
    }

    override fun onItemClicked(position: Int, spendingModel: SpendingModel) {
        val spendingModelWrapper = SpendingModelWrapper(position, spendingModel)
        startActivityForResult(
                activity.editPaymentActivityIntent(spendingModelWrapper),
                HomeActivityListener.EDIT_PAYMENT_REQUEST_CODE
        )
    }

    override fun onAddFirstSpending() {
        startActivityForResult(activity.addPaymentActivityIntent(), ADD_NEW_PAYMENT_REQUEST_CODE)
    }

    override fun onHeaderClicked() {
        startActivityForResult(activity.addPaymentActivityIntent(), ADD_NEW_PAYMENT_REQUEST_CODE)
    }

    override fun onItemEmpty() {
        list_financial_history.adapter = emptyAdapter
    }

    private fun setSpendingList(spendingModelList: MutableList<SpendingModel>) {
        list_financial_history.layoutManager = LinearLayoutManager(activity)
        when(spendingModelList.size) {
            0 -> onItemEmpty()
            else -> {
                spendingList.clear()
                spendingList.addAll(spendingModelList)
                list_financial_history.adapter = spendingListAdapter
                spendingListAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun setAutoCompleteView() {
        val filterAdapter = FilterAdapter(this)
        setFilterResultView(filterAdapter)
        setAutoSuggestionEditText(filterAdapter)
    }

    private fun setFilterResultView(filterAdapter: FilterAdapter) {
        filter_recycler_view.adapter = filterAdapter
        filter_recycler_view.layoutManager = StaggeredGridLayoutManager(
                1,
                StaggeredGridLayoutManager.HORIZONTAL
        )
    }

    private fun setAutoSuggestionEditText(filterAdapter: FilterAdapter) {
        val arrayAdapter = AutoCompleteSuggestionAdapter(activity)
        auto_complete_filter.setAdapter(arrayAdapter)
        setDropDownResultAction(filterAdapter, arrayAdapter)
    }

    private fun setDropDownResultAction(filterAdapter: FilterAdapter,
                                        arrayAdapter: AutoCompleteSuggestionAdapter) {
        val hashTagSuggestions = ArrayList<String>()
        auto_complete_filter.initComponent(
                CompositeDisposable(),
                autoCompleteListener(arrayAdapter, hashTagSuggestions)
        )
        auto_complete_filter.setOnItemClickListener { _, _, position, _ ->
            filterItemClicked(hashTagSuggestions, filterAdapter, position)
        }
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

    private fun onDataAdded(newData: SpendingModel) {
        list_financial_history.adapter = spendingListAdapter
        spendingList.add(0, newData)
        /*spendingListAdapter
                .notifyItemInserted(FinancialHistoryAdapter.NUMBER_OF_HEADER_ADAPTER);
        spendingListAdapter
                .notifyItemRangeInserted(
                        FinancialHistoryAdapter.NUMBER_OF_HEADER_ADAPTER,
                        spendingList.size() + FinancialHistoryAdapter.NUMBER_OF_HEADER_ADAPTER
                );
        financialHistoryList.scrollToPosition(FinancialHistoryAdapter.NUMBER_OF_HEADER_ADAPTER);*/

        //TODO RELEASE IF ANIMATION CAUSES MUCH BUGS
        spendingListAdapter.notifyDataSetChanged()
    }

    private fun onDataEdited(alteredData: SpendingModelWrapper) {
        spendingList[alteredData.position] = alteredData.spendingModel
        spendingListAdapter.notifyDataSetChanged()
    }

    private fun filterItemClicked(autoCompleteHashTagList: List<String>,
                                  filterAdapter: FilterAdapter,
                                  position: Int) {
        filterAdapter.addNewHashTag(autoCompleteHashTagList[position])
        spendingListAdapter.updateFilteredData(selectedFilterResult(
                filterAdapter.hashTagShownInAdapter)
        )
        auto_complete_filter.setText("")
        auto_complete_filter.requestFocus()
    }

    private fun updateAdapter() {
        spendingListAdapter.updateFilteredData(selectedFilterResult(auto_complete_filter.separatedString))
        auto_complete_filter.requestFocus()
    }

    override fun onFilterItemRemoved(hashTagList: List<String>) {
        when {
            hashTagList.isEmpty() -> spendingListAdapter.updateFilteredData(spendingList)
            else -> spendingListAdapter.updateFilteredData(selectedFilterResult(hashTagList))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when {
            requestCode == ADD_NEW_PAYMENT_REQUEST_CODE && resultCode == Activity.RESULT_OK ->
                onDataAdded(
                        (data!!.getParcelableExtra<Parcelable>(EXTRA_ADD_DATA_RESULT)
                                as SpendingModelWrapper
                                ).spendingModel)

            requestCode == EDIT_PAYMENT_REQUEST_CODE && resultCode == Activity.RESULT_OK ->
                onDataEdited(data!!.getParcelableExtra<Parcelable>(EXTRA_ADD_DATA_RESULT)
                        as SpendingModelWrapper)
        }
    }

    private fun autoCompleteListener(
            arrayAdapter: AutoCompleteSuggestionAdapter,
            listOfHashTag: MutableList<String>): SmartAutoCompleteTextView.AutoCompleteListener {

        return object : SmartAutoCompleteTextView.AutoCompleteListener {
            override fun finishedTyping(query: String) {
                listOfHashTag.clear()
                //TODO Dummy, List of resultnya diadd di sini
                listOfHashTag.add("makan")
                listOfHashTag.add("siang")
                listOfHashTag.add("liburan")
                listOfHashTag.add("pup")
                arrayAdapter.updateData(listOfHashTag)
            }

            override fun onTypingError(e: Throwable) {} //TODO Coming Soon

            override fun onEditTextEmptied() {} //TODO Coming Soon

            override fun onEnterKeyPressed() {
                updateAdapter()
            }
        }
    }

    private fun spendingModelList(): MutableList<SpendingModel> {
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

    companion object {

        fun createFragment(): ListFinancialHistoryFragment {
            return ListFinancialHistoryFragment()
        }
    }
}
