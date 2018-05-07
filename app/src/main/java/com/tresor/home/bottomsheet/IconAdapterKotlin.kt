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
class IconAdapterKotlin(private val iconModelList: MutableList<IconModel>)

    : RecyclerView.Adapter<IconAdapterViewHolder>(), IconAdapterViewHolder.iconViewHolderListener {
    override fun iconClicked() {
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IconAdapterViewHolder {
        val holderView = LayoutInflater.from(parent.context).inflate(R.layout.icon_list_layout, parent, false)
        return IconAdapterViewHolder(holderView, iconModelList)
    }

    override fun onBindViewHolder(holder: IconAdapterViewHolder, position: Int) {
        holder.bind(iconModelList[position].iconImageId, position, this)
    }

    override fun getItemCount(): Int {
        return iconModelList.size
    }

    fun getChoosenIcon(): Int {
        return iconModelList.indices.firstOrNull { iconModelList[it].isChoosen }
                ?: 0
    }
}