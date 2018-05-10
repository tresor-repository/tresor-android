package com.tresor.home.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.tresor.R
import com.tresor.home.viewholder.EmptyDailyListViewHolder

/**
 * Created by kris on 5/9/18. Tokopedia
 */
class EmptyDailyListAdapter(val listener: EmptyDailyListViewHolder.EmptyDailyListListener)
    : RecyclerView.Adapter<EmptyDailyListViewHolder>() {

    override fun onBindViewHolder(holder: EmptyDailyListViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int {
        return 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmptyDailyListViewHolder {
        val holderView = LayoutInflater.from(parent.context)
                .inflate(R.layout.empty_daily_list, parent, false)
        return EmptyDailyListViewHolder(holderView, listener)
    }


}