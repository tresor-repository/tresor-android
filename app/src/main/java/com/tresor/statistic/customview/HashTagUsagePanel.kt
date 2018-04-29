package com.tresor.statistic.customview

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.RelativeLayout
import com.tresor.R
import com.tresor.common.adapter.AutoCompleteSuggestionAdapter
import com.tresor.common.widget.implementable.DebouncingAutoCompleteTextView
import com.tresor.common.widget.template.SmartAutoCompleteTextView
import com.tresor.statistic.adapter.AnalyzeHashTagAdapter
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.analyze_hashtag_panel.view.*

/**
 * Created by kris on 4/29/18. Tokopedia
 */

class HashTagUsagePanel : RelativeLayout {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr)

    init {
        val inflater: LayoutInflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.analyze_hashtag_panel, this, true)
    }

    fun setData(analyzedHashTagList: List<String>,
                listener: HashTagUsagePanelListener) {

        val analyzeHashTagAdapter = AnalyzeHashTagAdapter(analyzedHashTagList)
        val arrayAdapter = AutoCompleteSuggestionAdapter(context)
        val listOfHashTag = mutableListOf<String>()

        setupAtutoCompleteTextView(listOfHashTag, autoCompleteTextView, arrayAdapter)
        setupHashTagListProperties(hashTagList, analyzeHashTagAdapter)

        autoCompleteTextView.setOnItemClickListener { _, _, position, _ ->
            onItemClickListener(analyzeHashTagAdapter, listOfHashTag, position)
        }

        okayButton.setOnClickListener(onOkayButtonClickedListener(analyzedHashTagList, listener))
    }

    private fun setupHashTagListProperties(hashTagList: RecyclerView,
                                           analyzeHashTagAdapter: AnalyzeHashTagAdapter) {
        hashTagList.adapter = analyzeHashTagAdapter
        hashTagList.layoutManager = LinearLayoutManager(context)
    }

    private fun setupAtutoCompleteTextView(listOfHashTag: MutableList<String>,
                                           autoCompleteTextView: DebouncingAutoCompleteTextView,
                                           arrayAdapter: AutoCompleteSuggestionAdapter) {
        autoCompleteTextView.initListener(
                compositeDisposable,
                debouncingAutoCompleteListener(arrayAdapter, listOfHashTag)
        )

        autoCompleteTextView.setAdapter(arrayAdapter)
    }

    private fun debouncingAutoCompleteListener(
            arrayAdapter: AutoCompleteSuggestionAdapter,
            listOfHashTag: MutableList<String>): SmartAutoCompleteTextView.AutoCompleteListener {
        return object : SmartAutoCompleteTextView.AutoCompleteListener {
            override fun finishedTyping(query: String) {
                listOfHashTag.clear()
                listOfHashTag.add("#makan")
                listOfHashTag.add("#gemuk")
                listOfHashTag.add("#kawai")
                /*listOfHashTag.add("#makan" + query)
                listOfHashTag.add("#gemuk" + query)
                listOfHashTag.add("#kawai" + query)*/
                arrayAdapter.updateData(listOfHashTag)
                arrayAdapter.notifyDataSetChanged()
            }

            override fun onTypingError(e: Throwable) {

            }

            override fun onEditTextEmptied() {

            }

            override fun onEnterKeyPressed() {

            }
        }
    }

    private fun onItemClickListener(adapter: AnalyzeHashTagAdapter,
                                    autoCompleteHashTagList: List<String>,
                                    position: Int) {
        val keyboard = context.getSystemService(Context.INPUT_METHOD_SERVICE)
                as InputMethodManager
        keyboard.hideSoftInputFromWindow(windowToken, 0)
        autoCompleteTextView.setText("")
        adapter.addNewItem(autoCompleteHashTagList[position])
        adapter.notifyDataSetChanged()
    }

    private fun onOkayButtonClickedListener(listHashTag: List<String>,
                                            listener: HashTagUsagePanelListener)
            : View.OnClickListener {
        return View.OnClickListener {
            listener.onFinishChoosingSpendingDialog(listHashTag)
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        compositeDisposable.dispose()
    }

    interface HashTagUsagePanelListener {
        fun onFinishChoosingSpendingDialog(hashTagList: List<String>)
    }

}
