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
import com.tresor.common.widget.implementable.FilterAutoCompleteTextView
import com.tresor.common.widget.template.SmartAutoCompleteTextView
import com.tresor.home.activity.addPaymentActivityIntent
import com.tresor.home.activity.editPaymentActivityIntent
import com.tresor.home.adapter.TodayPageAdapterKotlin
import com.tresor.home.inteface.HomeActivityListener
import com.tresor.home.inteface.HomeActivityListener.*
import com.tresor.home.model.SpendingModelWrapper
import com.tresor.home.viewholder.TodayPageAdapterViewHolder

import java.util.ArrayList

import io.reactivex.disposables.CompositeDisposable

import kotlinx.android.synthetic.main.fragment_list_financial_history.*

/**
 * Created by kris on 5/27/17. Tokopedia
 */

class ListFinancialHistoryFragment : Fragment(),
        TodayPageAdapterViewHolder.TodaySpendingAdapterListener,
        FilterAdapter.onFilterItemClicked {

    private val financialList: MutableList<SpendingModel> = mutableListOf()
    private val financialHistoryListAdapter: TodayPageAdapterKotlin =
            TodayPageAdapterKotlin(financialList, this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_list_financial_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAutoCompleteView()
        setSpendingList()
    }

    override fun onItemClicked(position: Int, spendingModel: SpendingModel) {
        val spendingModelWrapper = SpendingModelWrapper(position, spendingModel)
        startActivityForResult(
                activity.editPaymentActivityIntent(spendingModelWrapper),
                HomeActivityListener.EDIT_PAYMENT_REQUEST_CODE
        )
    }

    override fun onHeaderClicked() {
        startActivityForResult(activity.addPaymentActivityIntent(), ADD_NEW_PAYMENT_REQUEST_CODE)
    }

    private fun setSpendingList() {
        list_financial_history.layoutManager = LinearLayoutManager(activity)
        financialList.clear()
        financialList.addAll(spendingModelList())
        list_financial_history.adapter = financialHistoryListAdapter
        financialHistoryListAdapter.notifyDataSetChanged()
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
                autoCompleteListener(arrayAdapter, hashTagSuggestions, auto_complete_filter)
        )
        auto_complete_filter.setOnItemClickListener { _, _, position, _ ->
            filterItemClicked(hashTagSuggestions, filterAdapter, position)
        }
    }

    private fun selectedFilterResult(filteredTagList: List<String>): MutableList<SpendingModel> {
        return financialList.indices
                .filter { selectFilter(financialList[it].hashTagString, filteredTagList) }
                .mapTo(ArrayList()) { financialList[it] }
    }

    private fun selectFilter(hashTagString: String, listOfFilters: List<String>): Boolean {
        return listOfFilters.indices.any {
            hashTagString
                    .toLowerCase()
                    .contains(listOfFilters[it].toLowerCase())
        }
    }

    private fun onDataAdded(newData: SpendingModel) {
        financialList.add(0, newData)
        /*financialHistoryListAdapter
                .notifyItemInserted(FinancialHistoryAdapter.NUMBER_OF_HEADER_ADAPTER);
        financialHistoryListAdapter
                .notifyItemRangeInserted(
                        FinancialHistoryAdapter.NUMBER_OF_HEADER_ADAPTER,
                        financialList.size() + FinancialHistoryAdapter.NUMBER_OF_HEADER_ADAPTER
                );
        financialHistoryList.scrollToPosition(FinancialHistoryAdapter.NUMBER_OF_HEADER_ADAPTER);*/

        //TODO RELEASE IF ANIMATION CAUSES MUCH BUGS
        financialHistoryListAdapter.notifyDataSetChanged()
    }

    private fun onDataEdited(alteredData: SpendingModelWrapper) {
        financialList[alteredData.position] = alteredData.spendingModel
        financialHistoryListAdapter.notifyDataSetChanged()
    }

    fun onItemEdited() {
        financialHistoryListAdapter.notifyDataSetChanged()
    }

    private fun filterItemClicked(autoCompleteHashTagList: List<String>,
                                  filterAdapter: FilterAdapter,
                                  position: Int) {
        filterAdapter.addNewHashTag(autoCompleteHashTagList[position])
        financialHistoryListAdapter.updateData(selectedFilterResult(filterAdapter.hashTagShownInAdapter))
        auto_complete_filter.setText("")
        auto_complete_filter.requestFocus()
        /*autoCompleteTextView.addNewString(autoCompleteHashTagList.get(position));
                updateAdapter(autoCompleteTextView);
                autoCompleteTextView.setSelection(autoCompleteTextView.getText().length());*/
    }

    private fun autoCompleteListener(
            arrayAdapter: AutoCompleteSuggestionAdapter,
            listOfHashTag: MutableList<String>,
            autoCompleteTextView: FilterAutoCompleteTextView): SmartAutoCompleteTextView.AutoCompleteListener {

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
                updateAdapter(autoCompleteTextView)
            }
        }
    }

    private fun updateAdapter(autoCompleteTextView: FilterAutoCompleteTextView) {
        financialHistoryListAdapter.updateData(selectedFilterResult(autoCompleteTextView.separatedString))
        autoCompleteTextView.requestFocus()
    }

    override fun onFilterItemRemoved(hashTagList: List<String>) {
        when {
            hashTagList.isEmpty() -> financialHistoryListAdapter.updateData(financialList)
            else -> financialHistoryListAdapter.updateData(selectedFilterResult(hashTagList))
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
