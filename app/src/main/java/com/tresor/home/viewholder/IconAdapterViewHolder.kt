package com.tresor.home.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import com.tresor.R
import com.tresor.home.model.IconModel
import kotlinx.android.synthetic.main.icon_list_layout.view.*

/**
 * Created by kris on 5/6/18. Tokopedia
 */
class IconAdapterViewHolder(holderView: View, private val iconModelList: MutableList<IconModel>)
    : RecyclerView.ViewHolder(holderView) {

    fun bind(imageId: Int, position: Int, listener: iconViewHolderListener) = with(itemView) {
        setIconImage(icon_image, imageId)
        switchIconBackground(parent_layout, position)
        parent_layout.setOnClickListener {
            refreshIcons(position)
            listener.iconClicked()
        }
    }

    private fun setIconImage(image: ImageView, imageId: Int) {
        //TODO make function to pair data id with drawable id CODE RED
        when (imageId) {
            0 -> image.setImageResource(R.drawable.ic_spend_money_512)
            1 -> image.setImageResource(R.drawable.ic_burger_icon_big)
            2 -> image.setImageResource(R.drawable.ic_clothing)
            3 -> image.setImageResource(R.drawable.ic_tools)
            4 -> image.setImageResource(R.drawable.ic_health)
            5 -> image.setImageResource(R.drawable.ic_grocery)
            6 -> image.setImageResource(R.drawable.ic_electronics_alternative)
            7 -> image.setImageResource(R.drawable.ic_hygine)
            8 -> image.setImageResource(R.drawable.ic_transportation)
            else -> image.setImageResource(R.drawable.ic_transportation)
        }
    }

    private fun switchIconBackground(parentLayout: RelativeLayout, position: Int) {
        when(iconModelList[position].isChosen) {
            true -> parentLayout
                    .setBackgroundColor(
                            parentLayout.context.resources.getColor(R.color.brightBlue)
                    )
            else -> parentLayout.setBackgroundColor(
                    parentLayout.context.resources.getColor(android.R.color.transparent)
            )
        }
    }

    private fun refreshIcons(position: Int) {
        iconModelList.forEach { iconModel -> iconModel.isChosen = false}
        iconModelList[position].isChosen = true
    }

    interface iconViewHolderListener{
        fun iconClicked()
    }

}