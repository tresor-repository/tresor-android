package com.tresor.home.presenter

import com.tresor.common.adapter.AutoCompleteSuggestionAdapter
import com.tresor.common.model.viewmodel.SpendingModel
import com.tresor.common.widget.template.SmartAutoCompleteTextView
import com.tresor.home.fragment.TodaySpendingInterface
import java.util.ArrayList

/**
 * Created by kris on 5/11/18. Tokopedia
 */
class TodaySpendingPresenter(val view: TodaySpendingInterface): TodaySpendingPresenterInterface {

    override fun fetchSpendingList() {
        //TODO call updateList(spendingList) after call api
        val spendingList = spendingModelList()
        updateList(spendingList)
    }

    override fun updateFilter(hashTagList: List<String>) {
        //TODO put update list after api call
        updateList(spendingModelList())
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

    override fun addNewSpending(spendingModel: SpendingModel) {
        //TODO Add spending API
        val spendingList = spendingModelList()
        spendingList.add(0, spendingModel)
        updateList(spendingList)
    }

    override fun editNewSpending(position: Int, spendingModel: SpendingModel) {
        //TODO Edit Spending API
        val spendingList = spendingModelList()
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

    //TODO Archive algorithm if needed
/*    private fun selectedFilterResult(filteredTagList: List<String>): MutableList<SpendingModel> {
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
    }*/

}