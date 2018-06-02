package com.tresor.home.presenter

import com.tresor.common.adapter.AutoCompleteSuggestionAdapter
import com.tresor.common.constant.UniversalConstant
import com.tresor.common.model.viewmodel.SpendingListDatas
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
        updateList(generateSpendingDatas())
    }

    override fun updateFilter(startDate: String, endDate: String, hashTagList: List<String>) {
        //TODO put update list after api call
        updateList(generateSpendingDatas())
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
        view.addSpending(spendingModel)
    }

    override fun editNewSpending(position: Int, spendingModel: SpendingModel) {
        //TODO Edit Spending API
        view.editSpending(position, spendingModel)
    }

    override fun deleteSpending(adapterIndex: Int, spendingModel: SpendingModel) {
        view.deleteSpending(adapterIndex, spendingModel)
    }

    private fun updateList(spendingListDatas: SpendingListDatas) {
        val spendingList = spendingListDatas.spendingModelList
        when (spendingList.size) {
            0 -> view.onEmptySpending()
            else -> {
                view.renderSpending(spendingListDatas)
            }
        }
    }

    override fun loadMorePage(shownItemSize: Int, currentDataSize: Int) {
        val nextPage = shownItemSize / UniversalConstant.ItemsPerPage + 1
        when (shownItemSize < currentDataSize) {
        //TODO call this method after success
            true -> view.addDataFromNextPage(generateSpendingModelList())
        }
    }

    private fun generateSpendingDatas(): SpendingListDatas {
        return SpendingListDatas(generateSpendingModelList(),
                2500000.0,
                50)
    }

    private fun generateSpendingModelList(): MutableList<SpendingModel> {
        val list = ArrayList<SpendingModel>()
        for (i in 0..9) {
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