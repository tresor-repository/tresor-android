package com.tresor.common.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import com.tresor.R
import com.tresor.common.model.viewmodel.SpendingModel
import kotlinx.android.synthetic.main.financial_list_adapter.view.*

/**
 * Created by kris on 5/2/18. Tokopedia
 */

class ItemAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(spendingModel: SpendingModel,
             position: Int,
             listener: ItemAdapterListener) = with(itemView) {
        history_amount.text = spendingModel.amount
        var appendedHashtag = ""
        spendingModel.listHashTag.forEach { hastag -> appendedHashtag += hastag }
        history_hastag.text = appendedHashtag
        history_info.text = spendingModel.info
        history_date.text = spendingModel.date
        option_button.setOnClickListener { listener.onDelete(position) }
        item_place_holder.setOnClickListener { listener.onEditProduct(position, spendingModel) }
        setCardTheme(spendingModel.theme, itemView)
    }

    private fun setCardTheme(theme: Int, itemView: View) = with(itemView) {
        spending_icon.setImageResource(themeDrawable(theme))
    }

    private fun themeDrawable(theme: Int): Int {
        return when (theme) {
            0 -> R.drawable.ic_burger_icon_big
            1 -> R.drawable.ic_clothing
            2 -> R.drawable.ic_tools
            3 -> R.drawable.ic_health
            4 -> R.drawable.ic_grocery
            5 -> R.drawable.ic_electronics_alternative
            6 -> R.drawable.ic_hygine
            7 -> R.drawable.ic_transportation
            else -> R.mipmap.ic_cat_kitchen_dining_big
        }
    }

    interface ItemAdapterListener {
        fun onEditProduct(position: Int, spendingModel: SpendingModel)
        fun onDelete(position: Int)
    }

}
