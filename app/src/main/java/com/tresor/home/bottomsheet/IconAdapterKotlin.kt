package com.tresor.home.bottomsheet

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.tresor.R
import com.tresor.home.model.IconModel
import com.tresor.home.viewholder.IconAdapterViewHolder

/**
 * Created by kris on 5/6/18. Tokopedia
 */
class IconAdapterKotlin(private val iconModelList: MutableList<IconModel>,
                        private val listener: IconAdapterViewHolder.IconViewHolderListener)

    : RecyclerView.Adapter<IconAdapterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IconAdapterViewHolder {
        val holderView = LayoutInflater.from(parent.context).inflate(R.layout.icon_list_layout, parent, false)
        return IconAdapterViewHolder(holderView, iconModelList)
    }

    override fun onBindViewHolder(holder: IconAdapterViewHolder, position: Int) {
        holder.bind(iconModelList[position].iconImageId, position, listener)
    }

    override fun getItemCount(): Int {
        return iconModelList.size
    }

    fun getChosenIcon(): Int {
        return iconModelList.indices.firstOrNull { iconModelList[it].isChosen }
                ?: 0
    }

    fun getChosenIconDefaultHashTag(): String {
        return iconModelList.first { iconModel ->  iconModel.isChosen}.defaultTag
    }

}