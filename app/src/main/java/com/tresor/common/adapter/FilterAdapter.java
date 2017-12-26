package com.tresor.common.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tresor.R;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by kris on 10/8/17. Tokopedia
 */

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.FilterAdapterViewHolder> {

    private List<String> hashTagList;

    private onFilterItemClicked listener;

    public FilterAdapter(onFilterItemClicked listener) {
        this.hashTagList = new ArrayList<>();
        this.listener = listener;
    }

    public void addNewHashTag(String newHashTag) {
        hashTagList.add(newHashTag);
        notifyDataSetChanged();
    }

    public List<String> getHashTagShownInAdapter() {
        return hashTagList;
    }

    @Override
    public FilterAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View adapterView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hash_tag_suggestion_adapter, parent, false);
        return new FilterAdapterViewHolder(adapterView);
    }

    @Override
    public void onBindViewHolder(final FilterAdapterViewHolder holder, int position) {
        holder.suggestedHashTag.setText(hashTagList.get(position));
        holder.filterBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hashTagList.remove(holder.getAdapterPosition());
                notifyDataSetChanged();
                listener.onFilterItemRemoved(hashTagList);
            }
        });
    }

    @Override
    public int getItemCount() {
        return hashTagList.size();
    }

    class FilterAdapterViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout filterBackground;
        private TextView suggestedHashTag;

        FilterAdapterViewHolder(View itemView) {
            super(itemView);
            filterBackground = (LinearLayout) itemView.findViewById(R.id.filter_text_background);
            suggestedHashTag = (TextView) itemView.findViewById(R.id.suggested_hash_tag_text_view);
        }
    }

    public interface onFilterItemClicked {
        void onFilterItemRemoved(List<String> hashTagList);
    }

}
