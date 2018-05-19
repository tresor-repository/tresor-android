package com.tresor.home.fragment

import android.support.v7.widget.LinearLayoutManager
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
import com.tresor.home.inteface.HomeActivityListener
import com.tresor.home.model.SpendingModelWrapper
import com.tresor.home.presenter.SearchPresenter
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_search_hashtag.*

/**
 * Created by kris on 5/8/18. Tokopedia
 */
class SearchFragmentKotlin :
        SearchInterface,
        DateRangeFragmentKotlin(),
        SpendingListAdapter.SpendingItemListener,
        FilterAdapter.onFilterItemClicked {

    override fun onRemoveButtonClicked(adapterPosition: Int, spendingModel: SpendingModel) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private val presenter = SearchPresenter(this)

    override fun onEmptySpending() {
        onItemEmpty()
    }

    override fun renderSpending(spendingListDatas: SpendingListDatas) {
        search_recycler_view.adapter = SpendingListAdapter(spendingListDatas, this)
    }

    companion object {
        fun createInstance(): SearchFragmentKotlin {
            return SearchFragmentKotlin()
        }
    }

    override fun onFilterItemRemoved(hashTagList: MutableList<String>) {

    }

    override fun onItemClicked(position: Int, spendingModel: SpendingModel) {
        val spendingModelWrapper = SpendingModelWrapper(position, spendingModel)
        startActivityForResult(
                activity.editPaymentActivityIntent(spendingModelWrapper),
                HomeActivityListener.EDIT_PAYMENT_REQUEST_CODE
        )
    }

    override fun loadMoreItem(listSize: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onHeaderClicked() {
        startActivityForResult(activity.addPaymentActivityIntent(),
                HomeActivityListener.ADD_NEW_PAYMENT_REQUEST_CODE)
    }

    override fun onItemEmpty() {
        search_recycler_view.adapter = EmptySearchAdapter(false)
    }

    override fun initMainView() {
        search_recycler_view.layoutManager = (LinearLayoutManager(activity))
        search_recycler_view.adapter = EmptySearchAdapter(true)
        val filterAdapter = FilterAdapter(this)
        filter_recycler_view.adapter = filterAdapter
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

}