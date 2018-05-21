package com.tresor.statistic.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.tresor.R
import com.tresor.statistic.customview.HashTagUsagePanel

/**
 * Created by kris on 9/10/17. Tokopedia
 */

class AnalyzeHashTagAdapter(private val hashTagList: MutableList<String>,
                            private val listener: HashTagUsagePanel.HashTagUsagePanelListener)
    : RecyclerView.Adapter<AnalyzeHashTagAdapter.AnalyzeHashTagViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnalyzeHashTagViewHolder {
        val context = parent.context
        val adapterView = LayoutInflater
                .from(context).inflate(R.layout.analyze_hash_tag_adapter, parent, false)
        return AnalyzeHashTagViewHolder(context, adapterView)
    }

    override fun onBindViewHolder(holder: AnalyzeHashTagViewHolder, position: Int) {
        holder.analyzeTextView.text = hashTagList[position]
        holder.analyzeTextView.setOnClickListener { listener.onSelectHashtag(hashTagList[position]) }
        holder.graphColor.setBackgroundColor(colorCombination(holder.context, position))
        holder.removeHashTagButton.setOnClickListener(onRemoveHashTagButtonClickedListener(position))
    }

    override fun getItemCount(): Int {
        return hashTagList.size
    }

    inner class AnalyzeHashTagViewHolder(val context: Context, itemView: View) : RecyclerView.ViewHolder(itemView) {

        val analyzeTextView: TextView = itemView.findViewById(R.id.selected_hash_tag) as TextView
        val graphColor: View = itemView.findViewById(R.id.graph_color)
        val removeHashTagButton: ImageView = itemView.findViewById(R.id.remove_hash_tag_button) as ImageView

    }

    private fun colorCombination(context: Context, position: Int): Int {
        return when (position) {
            0 -> context.resources.getColor(R.color.pie1)
            1 -> context.resources.getColor(R.color.pie2)
            2 -> context.resources.getColor(R.color.pie3)
            3 -> context.resources.getColor(R.color.pie4)
            4 -> context.resources.getColor(R.color.pie5)
            else -> context.resources.getColor(R.color.pie6)
        }
    }

    private fun onRemoveHashTagButtonClickedListener(position: Int): View.OnClickListener {
        return View.OnClickListener {
            hashTagList.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeRemoved(position, hashTagList.size)
        }
    }

    fun addNewItem(newlyAddedHashTag: String) {
        hashTagList.add(newlyAddedHashTag)
    }

}
