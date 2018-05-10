package com.tresor.home.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tresor.R

/**
 * Created by kris on 5/10/18. Tokopedia
 */
class EmptySearchAdapter(private val initiateSearch: Boolean)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {

    }

    override fun getItemCount(): Int {
        return 1
    }

    override fun getItemViewType(position: Int): Int {
        return when(initiateSearch) {
            true -> R.layout.initial_search_page
            else -> R.layout.empty_search_list
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val holderView = LayoutInflater.from(
                parent.context).inflate(viewType, parent, false
        )
        return EmptySearchViewHolder(holderView)
    }

    class EmptySearchViewHolder(holderView: View) : RecyclerView.ViewHolder(holderView)
}