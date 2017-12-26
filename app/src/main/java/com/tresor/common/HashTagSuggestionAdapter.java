package com.tresor.common;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tresor.R;
import com.tresor.common.model.HashTagFilterModel;

import java.util.List;

/**
 * Created by kris on 8/10/17. Tokopedia
 */

public class HashTagSuggestionAdapter extends RecyclerView.Adapter<HashTagSuggestionAdapter.HashTagSuggestionHolder>{

    private Context context;
    private List<HashTagFilterModel> hashTagList;
    private onSuggestedHashTagClickedListener listener;

    public HashTagSuggestionAdapter(List<HashTagFilterModel> hashTagList,
                                    onSuggestedHashTagClickedListener listener) {
        this.hashTagList = hashTagList;
        this.listener = listener;
    }

    @Override
    public HashTagSuggestionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View adapterView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hash_tag_suggestion_adapter, parent, false);
        return new HashTagSuggestionHolder(adapterView);
    }

    @Override
    public void onBindViewHolder(final HashTagSuggestionHolder holder, int position) {
        final String hashTagString = hashTagList.get(position).getHashTagString();
        holder.suggestedHashTag.setText(hashTagList.get(position).getHashTagString());
        if(hashTagList.get(position).isSelected()) {
            holder.filterBackground.setBackground(context.getResources()
                    .getDrawable(R.drawable.selected_filter));
            holder.suggestedHashTag.setTextColor(Color.WHITE);
        } else {
            holder.filterBackground.setBackground(context.getResources()
                    .getDrawable(R.drawable.square_with_border));
            holder.suggestedHashTag.setTextColor(Color.BLACK);
        }
        holder.suggestedHashTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hashTagList.get(holder.getAdapterPosition()).isSelected()) {
                    clearSelectedList();
                    listener.clearFilter();
                } else {
                    clearSelectedList();
                    hashTagList.get(holder.getAdapterPosition()).setSelected(true);
                    listener.hashTagSelected(hashTagString);
                }
            }
        });
    }

    private void clearSelectedList() {
        for (int i = 0; i < hashTagList.size(); i++) {
            hashTagList.get(i).setSelected(false);
        }
    }

    @Override
    public int getItemCount() {
        return hashTagList.size();
    }

    class HashTagSuggestionHolder extends RecyclerView.ViewHolder{
        private LinearLayout filterBackground;
        private TextView suggestedHashTag;

        HashTagSuggestionHolder(View itemView) {
            super(itemView);
            filterBackground = (LinearLayout) itemView.findViewById(R.id.filter_text_background);
            suggestedHashTag = (TextView) itemView.findViewById(R.id.suggested_hash_tag_text_view);
        }
    }

    public interface onSuggestedHashTagClickedListener {
        void hashTagSelected(String selectedHashtag);
        void clearFilter();
    }

}
