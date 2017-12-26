package com.tresor.statistic;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.tresor.R;

/**
 * Created by kris on 9/6/17. Tokopedia
 */

public class HashTagBarChart extends LinearLayout{

    private BarChart hashTagBarChart;

    public HashTagBarChart(Context context) {
        super(context);
        initView(context);
    }

    public HashTagBarChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public HashTagBarChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view =  inflater.inflate(R.layout.view_bar_chart, this, true);
        hashTagBarChart = (BarChart) view.findViewById(R.id.hashtag_bar_chart);
    }
}
