package com.tresor.common.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tresor.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kris on 9/22/17. Tokopedia
 */

public class AutoCompleteSuggestionAdapter extends ArrayAdapter<String>{
    private List<String> listOfRecommendation;

    public AutoCompleteSuggestionAdapter(Context context) {
        super(context, 0);
        listOfRecommendation = new ArrayList<>();
    }

    private class AutoCompleteViewHolder {
        TextView autoComplete;
    }

    @Override
    public int getCount() {
        return listOfRecommendation.size();
    }

    @Nullable
    @Override
    public String getItem(int position) {
        return listOfRecommendation.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        AutoCompleteSuggestionAdapter.AutoCompleteViewHolder holder = new AutoCompleteSuggestionAdapter.AutoCompleteViewHolder();
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.plain_adapter, parent, false);
            holder.autoComplete = (TextView) convertView.findViewById(R.id.generic_text_view);
            convertView.setTag(holder);
        } else {
            holder = (AutoCompleteSuggestionAdapter.AutoCompleteViewHolder) convertView.getTag();
        }
        holder.autoComplete.setText(listOfRecommendation.get(position));
        return convertView;
    }

    public void updateData(List<String> listOfRecommendedHashTag) {
        this.listOfRecommendation = listOfRecommendedHashTag;
        notifyDataSetChanged();
    }
}
