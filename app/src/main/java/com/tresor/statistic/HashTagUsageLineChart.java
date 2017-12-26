package com.tresor.statistic;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.tresor.R;
import com.tresor.home.model.HashTagStatisticModel;
import com.tresor.statistic.adapter.AnalyzeHashTagAdapter;
import com.tresor.statistic.dialog.AnalyzeHashTagSpendingDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kris on 9/2/17. Tokopedia
 */

public class HashTagUsageLineChart extends LinearLayout{

    private LineChart hashTagLineChart;
    private TextView selectHashTagButton;
    private Context context;

    public HashTagUsageLineChart(Context context) {
        super(context);
        initView(context);
    }

    public HashTagUsageLineChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public HashTagUsageLineChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view =  inflater.inflate(R.layout.view_multi_line_chart, this, true);
        this.context = context;
        selectHashTagButton = (TextView) view.findViewById(R.id.select_hastag_button);
        hashTagLineChart = (LineChart) view.findViewById(R.id.hashtag_multi_line_chart);
        hashTagLineChart.setDrawGridBackground(false);
        hashTagLineChart.getAxisLeft().setDrawGridLines(false);
        hashTagLineChart.getXAxis().setDrawGridLines(false);
    }

    public void setLineChart(Context context, List<HashTagStatisticModel> statisticModels) {
        List<String> hashtagList = dummyTop5HashTagList();
        setChartData(context, hashtagList);
    }

    private void setChartData(Context context, List<String> hashtagList) {
        selectHashTagButton.setOnClickListener(onHashTagButtonClickedListener(hashtagList));
        LineData lineData = new LineData(multipleHashtagLine(context, hashtagList));
        lineData.setDrawValues(false);
        hashTagLineChart.setData(lineData);
        hashTagLineChart.animateX(1000);
    }

    private List<ILineDataSet> multipleHashtagLine(Context context, List<String> hashTags) {
        List<ILineDataSet> lineDataSets = new ArrayList<>();
        for(int i = 0; i < hashTags.size(); i++) {
            LineDataSet lineDataSet = new LineDataSet(getLineAmountHashTag(),
                    hashTags.get(i));
            setDatasetMode(lineDataSet, getColor(context, i), false);
            lineDataSets.add(lineDataSet);
        }
        return lineDataSets;
    }

    private List<Entry> getLineAmountHashTag() {
        List<Entry> lineEntries = new ArrayList<>();
        double range = 100;
        for (int i = 0; i < 12; i++) {
            if(i > 0) {
                lineEntries.add(new Entry(i,
                        (float) (Math.random() * range) + lineEntries.get(i-1).getY()));
            } else {
                lineEntries.add(new Entry(i, (float) (Math.random() * range)));
            }
        }
        return lineEntries;
    }

    private int getColor(Context context, int position) {
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
                return context.getResources().getColor(R.color.pie1);
        }
    }

    private void setDatasetMode(LineDataSet set1, int color, boolean drawFilled) {
        set1.setDrawCircles(true);
        set1.setDrawFilled(drawFilled);
        if (drawFilled) {
            set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            set1.setCubicIntensity(0.2f);
        } set1.setFillColor(color);
        set1.setLineWidth(3.6f);
        set1.setCircleRadius(4f);
        set1.setCircleColor(color);
        set1.setHighLightColor(Color.rgb(244, 117, 117));
        set1.setColor(color);
        set1.setFillAlpha(100);
        set1.setDrawHorizontalHighlightIndicator(false);
        set1.setFillFormatter(new IFillFormatter() {
            @Override
            public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                return -10;
            }
        });
    }

    private OnClickListener onHashTagButtonClickedListener(final List<String> hashtagList) {
        return new OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = ((Activity)context).getFragmentManager().beginTransaction();
                Fragment prev = ((Activity)context).getFragmentManager().findFragmentByTag("dialog");
                if (prev != null) {
                    ft.remove(prev);
                }
                ft.addToBackStack(null);
                AnalyzeHashTagSpendingDialog analyzeDialog = AnalyzeHashTagSpendingDialog
                        .createDialog((ArrayList<String>) hashtagList);
                analyzeDialog.show(ft, "dialog");
            }
        };
    }

    private ArrayList<String> dummyTop5HashTagList() {
        ArrayList<String> dummyList = new ArrayList<>();
        dummyList.add("#makan");
        dummyList.add("#makanterus");
        dummyList.add("#makansampendut");
        return dummyList;
    }

    public void receivedListOfHashTagComparison(List<String> hashTagsToCompare) {
        setChartData(context, hashTagsToCompare);
    }
}
