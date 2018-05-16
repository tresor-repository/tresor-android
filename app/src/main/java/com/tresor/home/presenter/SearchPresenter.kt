package com.tresor.home.presenter

import com.tresor.common.adapter.AutoCompleteSuggestionAdapter
import com.tresor.common.model.viewmodel.SpendingModel
import com.tresor.common.widget.template.SmartAutoCompleteTextView
import com.tresor.home.fragment.SearchInterface
import java.util.ArrayList

/**
 * Created by kris on 5/13/18. Tokopedia
 */
class SearchPresenter(val view: SearchInterface) : SearchPresenterInterface {

    override fun fetchSearchData(startDate: String, endDate: String, hashTagList: List<String>) {
        //TODO fetch search data here
        val spendingList = generateSpendingModelList()
        updateList(spendingList)
    }

    override fun updateFilter(startDate: String, endDate: String, hashTagList: List<String>) {
        //TODO put update list after api call
        updateList(generateSpendingModelList())
    }

    override fun autoCompletePresenterListener(startDate: String,
                                               endDate: String,
                                               hashTagSuggestions: MutableList<String>,
                                               arrayAdapter: AutoCompleteSuggestionAdapter):
            SmartAutoCompleteTextView.AutoCompleteListener {
        return object : SmartAutoCompleteTextView.AutoCompleteListener {
            override fun finishedTyping(query: String) {
                //TODO Dummy, List of resultnya diadd di sini
                hashTagSuggestions.add("makan")
                hashTagSuggestions.add("siang")
                hashTagSuggestions.add("liburan")
                hashTagSuggestions.add("pup")
                arrayAdapter.updateData(hashTagSuggestions)
            }

            override fun onTypingError(e: Throwable) {} //TODO Coming Soon

            override fun onEditTextEmptied() {} //TODO Coming Soon

            override fun onEnterKeyPressed() {
                //TODO Dummy, List of resultnya diadd di sini
                //updateFilter(hashTagSuggestions)
            }
        }
    }

    override fun addNewSpending(spendingModel: SpendingModel) {
        //TODO Add spending API
        val spendingList = generateSpendingModelList()
        spendingList.add(0, spendingModel)
        updateList(spendingList)
    }

    override fun editNewSpending(position: Int, spendingModel: SpendingModel) {
        //TODO Edit Spending API
        val spendingList = generateSpendingModelList()
        spendingList[position] = spendingModel
        updateList(spendingList)
    }

    private fun updateList(spendingList: MutableList<SpendingModel>) {
        when (spendingList.size) {
            0 -> view.onEmptySpending()
            else -> {
                view.renderSpending(spendingList)
            }
        }
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