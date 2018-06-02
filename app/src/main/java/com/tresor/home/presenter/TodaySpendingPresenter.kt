package com.tresor.home.presenter

import com.tresor.common.adapter.AutoCompleteSuggestionAdapter
import com.tresor.common.constant.UniversalConstant
import com.tresor.common.model.viewmodel.SpendingListDatas
import com.tresor.common.model.viewmodel.SpendingModel
import com.tresor.common.widget.template.SmartAutoCompleteTextView
import com.tresor.home.fragment.TodaySpendingInterface
import java.util.ArrayList

/**
 * Created by kris on 5/11/18. Tokopedia
 */
class TodaySpendingPresenter(val view: TodaySpendingInterface) : TodaySpendingPresenterInterface {

    override fun loadMorePage(shownItemSize: Int, currentDataSize: Int) {
        val nextPage = shownItemSize / UniversalConstant.ItemsPerPage + 1
        when (shownItemSize < currentDataSize) {
        //TODO call this method after success
            true -> view.addDataFromNextPage(spendingModelList())
        }
    }

    override fun fetchSpendingList() {
        //TODO call updateList(spendingList) after call api
        updateList(generateSpendingDatas())
    }

    override fun updateFilter(hashTagList: List<String>) {
        //TODO put update list after api call
        updateList(generateSpendingDatas())
    }

    override fun autoCompletePresenterListener(hashTagSuggestions: MutableList<String>,
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
                updateFilter(hashTagSuggestions)
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

    override fun deleteSpendingRecord(position: Int, spendingModel: SpendingModel) {
        //TODO Delete Spending Record API
        view.deleteSpending(position, spendingModel)
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

    private fun generateSpendingDatas(): SpendingListDatas {
        return SpendingListDatas(spendingModelList(),
                2500000.0,
                50)
    }

    private fun spendingModelList(): MutableList<SpendingModel> {
        val list = ArrayList<SpendingModel>()
        for (i in 0..9) {
            val hashTagList = ArrayList<String>()
            hashTagList.add("Makan")
            hashTagList.add("Siang")
            hashTagList.add("Liburan")
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