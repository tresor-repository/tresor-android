package com.tresor.home.fragment

import android.app.Activity
import android.content.Intent
import android.os.Parcelable
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.View
import android.widget.EditText
import com.tresor.R
import com.tresor.common.adapter.AutoCompleteSuggestionAdapter
import com.tresor.common.adapter.FilterAdapter
import com.tresor.common.fragment.DateRangeFragmentKotlin
import com.tresor.common.model.viewmodel.SpendingListDatas
import com.tresor.common.model.viewmodel.SpendingModel
import com.tresor.home.activity.addPaymentActivityIntent
import com.tresor.home.activity.editPaymentActivityIntent
import com.tresor.home.adapter.EmptySearchAdapter
import com.tresor.home.adapter.SpendingListAdapter
import com.tresor.home.implementables.PaymentItemSwipeDeleteListener
import com.tresor.home.implementables.SpendingItemScrollListener
import com.tresor.home.inteface.HomeActivityListener
import com.tresor.home.model.SpendingModelWrapper
import com.tresor.home.presenter.SearchPresenter
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_list_financial_history.*
import kotlinx.android.synthetic.main.fragment_search_hashtag.*

/**
 * Created by kris on 5/8/18. Tokopedia
 */
class SearchFragmentKotlin :
        SearchInterface,
        DateRangeFragmentKotlin(),
        SpendingListAdapter.SpendingItemListener,
        FilterAdapter.onFilterItemClicked {

    private val presenter = SearchPresenter(this)

    companion object {
        fun createInstance(): SearchFragmentKotlin {
            return SearchFragmentKotlin()
        }
    }

    override fun onEmptySpending() {
        onItemEmpty()
    }

    override fun renderSpending(spendingListDatas: SpendingListDatas) {
        search_recycler_view.adapter = SpendingListAdapter(spendingListDatas, this)
        val itemTouchHelper = ItemTouchHelper(
                PaymentItemSwipeDeleteListener(
                        activity,
                        search_recycler_view.adapter as SpendingListAdapter)
        )
        itemTouchHelper.attachToRecyclerView(search_recycler_view)
    }

    override fun onFilterItemRemoved(hashTagList: MutableList<String>) {

    }

    override fun onRemoveButtonClicked(adapterPosition: Int, spendingModel: SpendingModel) {
        presenter.deleteSpending(adapterPosition, spendingModel)
    }

    override fun onItemClicked(adapterPosition: Int, spendingModel: SpendingModel) {
        val spendingModelWrapper = SpendingModelWrapper(adapterPosition, spendingModel)
        startActivityForResult(
                activity.editPaymentActivityIntent(spendingModelWrapper),
                HomeActivityListener.EDIT_PAYMENT_REQUEST_CODE
        )
    }

    override fun loadMoreItem(listSize: Int) {
        presenter.loadMorePage(
                listSize,
                (search_recycler_view.adapter as SpendingListAdapter).currentDataSize()
        )
    }

    override fun onHeaderClicked() {
        startActivityForResult(activity.addPaymentActivityIntent(),
                HomeActivityListener.ADD_NEW_PAYMENT_REQUEST_CODE)
    }

    override fun deleteSpending(adapterIndex: Int, spendingModel: SpendingModel) {
        (search_recycler_view.adapter as SpendingListAdapter)
                .removeData(adapterIndex, spendingModel)
    }

    override fun addSpending(spendingModel: SpendingModel) {
        search_recycler_view.scrollToPosition(0)
        (search_recycler_view.adapter as SpendingListAdapter).addNewData(spendingModel)
    }

    override fun editSpending(adapterIndex: Int, spendingModel: SpendingModel) {
        (search_recycler_view.adapter as SpendingListAdapter)
                .editData(adapterIndex, spendingModel)
    }

    override fun addDataFromNextPage(nextPageSpendings: MutableList<SpendingModel>) {
        (search_recycler_view.adapter as SpendingListAdapter).addNewPageData(nextPageSpendings)
    }

    override fun onItemEmpty() {
        search_recycler_view.adapter = EmptySearchAdapter(false)
    }

    override fun initMainView() {
        search_recycler_view.layoutManager = (LinearLayoutManager(activity))
        search_recycler_view.adapter = EmptySearchAdapter(true)
        val filterAdapter = FilterAdapter(this)
        filter_recycler_view_search.adapter = filterAdapter
        val arrayAdapter = AutoCompleteSuggestionAdapter(activity)
        val hashTagSuggestions = mutableListOf<String>()
        auto_complete_search_filter.setAdapter(arrayAdapter)
        auto_complete_search_filter.initComponent(
                CompositeDisposable(),
                presenter.autoCompletePresenterListener(
                        start_date_field.text.toString(),
                        end_date_field.text.toString(),
                        hashTagSuggestions,
                        arrayAdapter
                )
        )
        auto_complete_search_filter.setOnItemClickListener { _, _, position, _ ->
            filterItemClicked(hashTagSuggestions, filterAdapter, position)
        }
        search_button.setOnClickListener { setSpending(filterAdapter.hashTagShownInAdapter) }

    }

    private fun setSpending(filterHashTag: MutableList<String>) {
        search_recycler_view.layoutManager = LinearLayoutManager(activity)
        presenter.fetchSearchData(
                start_date_field.text.toString(),
                end_date_field.text.toString(),
                filterHashTag)

        search_recycler_view.addOnScrollListener(SpendingItemScrollListener(this))
        search_recycler_view.setHasFixedSize(true)
        search_recycler_view.setItemViewCacheSize(20)
        search_recycler_view.isDrawingCacheEnabled = true
        search_recycler_view.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_search_hashtag
    }

    private fun filterItemClicked(autoCompleteHashTagList: List<String>,
                                  filterAdapter: FilterAdapter,
                                  position: Int) {
        filterAdapter.addNewHashTag(autoCompleteHashTagList[position])
        auto_complete_search_filter.setText("")
        auto_complete_search_filter.requestFocus()
    }

    override fun startDateEditText(): EditText {
        return start_date_field
    }

    override fun endDateEditText(): EditText {
        return end_date_field
    }

    override fun startDateChanged(date: Int, month: Int, year: Int) {

    }

    override fun endDateChanged(date: Int, month: Int, year: Int) {

    }

    private fun onDataAdded(newData: SpendingModel) {
        presenter.addNewSpending(newData)
    }

    private fun onDataEdited(alteredData: SpendingModelWrapper) {
        presenter.editNewSpending(alteredData.adapterPosition, alteredData.spendingModel)
    }

    private fun onInitialDataAdded(newData: SpendingModel) {
        val newSpendingList = mutableListOf<SpendingModel>()
        newSpendingList.add(newData)
        list_financial_history.adapter = SpendingListAdapter(
                SpendingListDatas(newSpendingList, newData.amountUnformatted, 1), this
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when {
        //TODO Different Type of add and edit, need new add page
            requestCode == HomeActivityListener.ADD_NEW_PAYMENT_REQUEST_CODE && resultCode == Activity.RESULT_OK ->
                onDataAdded(
                        (data!!.getParcelableExtra<Parcelable>(HomeActivityListener.EXTRA_ADD_DATA_RESULT)
                                as SpendingModelWrapper
                                ).spendingModel)

            requestCode == HomeActivityListener.EDIT_PAYMENT_REQUEST_CODE && resultCode == Activity.RESULT_OK ->
                onDataEdited(data!!.getParcelableExtra<Parcelable>(HomeActivityListener.EXTRA_ADD_DATA_RESULT)
                        as SpendingModelWrapper)

            requestCode == HomeActivityListener.ADD_INITIAL_PAYMENT_CODE && resultCode == Activity.RESULT_OK ->
                onInitialDataAdded((data!!.getParcelableExtra<Parcelable>(HomeActivityListener.EXTRA_ADD_DATA_RESULT)
                        as SpendingModelWrapper
                        ).spendingModel)
        }
    }

}