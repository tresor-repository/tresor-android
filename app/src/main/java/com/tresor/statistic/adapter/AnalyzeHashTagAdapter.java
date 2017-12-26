package com.tresor.statistic.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tresor.R;

import java.util.List;

/**
 * Created by kris on 9/10/17. Tokopedia
 */

public class AnalyzeHashTagAdapter extends RecyclerView.Adapter<AnalyzeHashTagAdapter
        .AnalyzeHashTagViewHolder>{

    private List<String> hashTagList;
    private Context context;

    public AnalyzeHashTagAdapter(List<String> hashTagList) {
        this.hashTagList = hashTagList;
    }

    @Override
    public AnalyzeHashTagViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View adapterView = LayoutInflater
                .from(context).inflate(R.layout.analyze_hash_tag_adapter, parent, false);
        return new AnalyzeHashTagViewHolder(adapterView);
    }

    @Override
    public void onBindViewHolder(AnalyzeHashTagViewHolder holder, int position) {
        holder.analyzeTextView.setText(hashTagList.get(position));
        holder.graphColor.setBackgroundColor(colorCombination(position));
        holder.removeHashTagButton.setOnClickListener(onRemoveHashTagButtonClickedListener(position));
    }

    @Override
    public int getItemCount() {
        return hashTagList.size();
    }

    class AnalyzeHashTagViewHolder extends RecyclerView.ViewHolder {

        private TextView analyzeTextView;
        private View graphColor;
        private ImageView removeHashTagButton;

        AnalyzeHashTagViewHolder(View itemView) {
            super(itemView);
            analyzeTextView = (TextView) itemView.findViewById(R.id.selected_hash_tag);
            graphColor = itemView.findViewById(R.id.graph_color);
            removeHashTagButton = (ImageView) itemView.findViewById(R.id.remove_hash_tag_button);
        }
    }

    private int colorCombination(int position) {
        switch (position) {
            case 0:
                return context.getResources().getColor(R.color.pie1);
            case 1:
                return context.getResources().getColor(R.color.pie2);
            case 2:
                return context.getResources().getColor(R.color.pie3);
            case 3:
                return context.getResources().getColor(R.color.pie4);
            case 4:
                return context.getResources().getColor(R.color.pie5);
            default:
                return context.getResources().getColor(R.color.pie6);
        }
    }

    public void setData(List<String> hashTagList) {
        this.hashTagList = hashTagList;
    }

    private View.OnClickListener onRemoveHashTagButtonClickedListener(final int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hashTagList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeRemoved(position, hashTagList.size());
            }
        };
    }

    public void addNewItem(String newlyAddedHashTag) {
        hashTagList.add(newlyAddedHashTag);
    }

}
