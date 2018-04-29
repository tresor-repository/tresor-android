package com.tresor.statistic.hashtagusage.fragment

import android.app.Activity
import android.app.DialogFragment
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.tresor.R
import com.tresor.common.adapter.AutoCompleteSuggestionAdapter
import com.tresor.common.widget.implementable.DebouncingAutoCompleteTextView
import com.tresor.common.widget.template.SmartAutoCompleteTextView
import com.tresor.statistic.adapter.AnalyzeHashTagAdapter

import java.util.ArrayList

import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.analyze_hashtag_panel.*

/**
 * Created by kris on 4/16/18. Tokopedia
 */

class HashTagUsageDialog : DialogFragment() {

    private var compositeDisposable: CompositeDisposable? = null

    private var listener : HashTagUsageDialogListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as HashTagUsageDialogListener
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        listener = activity as HashTagUsageDialogListener
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle): View? {
        val view = inflater.inflate(R.layout.analyze_hashtag_panel, container)
        compositeDisposable = CompositeDisposable()

        val autoCompleteList = ArrayList<String>()

        val analyzedHashTagList = arguments.getStringArrayList(HASH_TAG_LIST_KEY)

        initView(autoCompleteList, analyzedHashTagList)

        return view
    }

    private fun initView(listOfHashTag: MutableList<String>,
                         analyzedHashTagList: List<String>) {

        val analyzeHashTagAdapter = AnalyzeHashTagAdapter(analyzedHashTagList)
        val arrayAdapter = AutoCompleteSuggestionAdapter(activity)

        setupAtutoCompleteTextView(listOfHashTag, autoCompleteTextView, arrayAdapter)
        setupHashTagListProperties(hashTagList, analyzeHashTagAdapter)

        autoCompleteTextView.setOnItemClickListener {
            parent, _view, position, id ->
            onItemClickListener(analyzeHashTagAdapter, listOfHashTag, position)}

        okayButton.setOnClickListener(onOkayButtonClickedListener(analyzedHashTagList))
    }

    private fun setupHashTagListProperties(hashTagList: RecyclerView,
                                           analyzeHashTagAdapter: AnalyzeHashTagAdapter) {
        hashTagList.adapter = analyzeHashTagAdapter
        hashTagList.layoutManager = LinearLayoutManager(activity)
    }

    private fun setupAtutoCompleteTextView(listOfHashTag: MutableList<String>, autoCompleteTextView: DebouncingAutoCompleteTextView, arrayAdapter: AutoCompleteSuggestionAdapter) {
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
                listOfHashTag.add("#makan" + query)
                listOfHashTag.add("#gemuk" + query)
                listOfHashTag.add("#kawai" + query)
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
                                    position : Int)  {
        adapter.addNewItem(autoCompleteHashTagList[position])
        adapter.notifyDataSetChanged()
    }

    private fun onOkayButtonClickedListener(listHashTag: List<String>): View.OnClickListener {
        return View.OnClickListener {
            listener!!.onFinishChoosingSpendingDialog(listHashTag)
            dismiss()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable!!.dispose()
    }

    interface HashTagUsageDialogListener {
        fun onFinishChoosingSpendingDialog(hashTagList: List<String>)
    }

    companion object {

        private val HASH_TAG_LIST_KEY = "hash_tag_list_key"

        fun createDialog(hashTagList: ArrayList<String>): HashTagUsageDialog {
            val dialog = HashTagUsageDialog()
            val bundle = Bundle()
            bundle.putStringArrayList(HASH_TAG_LIST_KEY, hashTagList)
            dialog.arguments = bundle
            return dialog
        }
    }
}
