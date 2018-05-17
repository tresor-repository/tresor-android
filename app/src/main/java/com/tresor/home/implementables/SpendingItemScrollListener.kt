package com.tresor.home.implementables

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.tresor.common.constant.UniversalConstant
import com.tresor.home.adapter.SpendingListAdapter

/**
 * Created by kris on 5/16/18. Tokopedia
 */

class SpendingItemScrollListener(val listener: SpendingListAdapter.SpendingItemListener) :
        RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val totalItem = recyclerView!!.layoutManager.itemCount
        val lastVisibleItemPosition = (recyclerView.layoutManager as LinearLayoutManager)
                .findLastVisibleItemPosition()
        if (lastVisibleItemPosition == totalItem - 1
                && totalItem >= UniversalConstant.ItemsPerPage
                && totalItem % UniversalConstant.ItemsPerPage == 0) {
            listener.loadMoreItem(totalItem)
        }

    }

    override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
    }

}