package com.tresor.statistic;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.tresor.R;
import com.tresor.home.model.HashTagStatisticModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kris on 8/27/17. Tokopedia
 */

public class HashTagPieChart extends LinearLayout{

    private PieChart pieChart;

    public HashTagPieChart(Context context) {
        super(context);
        initView(context);
    }

    public HashTagPieChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public HashTagPieChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view =  inflater.inflate(R.layout.view_pie_chart, this, true);
        pieChart = (PieChart) view
                .findViewById(R.id.hashtag_pie_chart);
        pieChart.setDrawHoleEnabled(false);
    }

    public void setChartData(Context context, List<HashTagStatisticModel> statisticModels) {
        PieDataSet dataSet = new PieDataSet(getPercentageAmount(statisticModels),
                "Percentage of Hashtags");
        dataSet.setColors(colorList(context));
        PieData pieData = new PieData(dataSet);
        pieChart.getDescription().setEnabled(false);
        pieChart.getLegend()
                .setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        pieChart.setDrawCenterText(true);
        pieChart.setData(pieData);
        pieChart.animate();
    }

    private List<Integer> colorList(Context context) {
        List<Integer> listColor = new ArrayList<>();
        listColor.add(context.getResources().getColor(R.color.pie1));
        listColor.add(context.getResources().getColor(R.color.pie2));
        listColor.add(context.getResources().getColor(R.color.pie3));
        listColor.add(context.getResources().getColor(R.color.pie4));
        listColor.add(context.getResources().getColor(R.color.pie5));
        listColor.add(context.getResources().getColor(R.color.pie6));
        return listColor;
    }

    private List<PieEntry> getPercentageAmount(List<HashTagStatisticModel> hashTagStatisticList) {
        List<PieEntry> pieEntries = new ArrayList<>();
        double totalAmount = getTotalAmount(hashTagStatisticList);
        for (int i = 0; i < hashTagStatisticList.size(); i++) {
            float percentile = Double.valueOf(hashTagStatisticList.get(i).getTotalPeriodicAmount()/totalAmount).floatValue();
            PieEntry pieEntry = new PieEntry(percentile, hashTagStatisticList.get(i).getHashTag());
            pieEntries.add(pieEntry);
        }
        return pieEntries;
    }

    private double getTotalAmount(List<HashTagStatisticModel> hashTagStatisticList) {
        double amount = 0;
        for (int i = 0; i < hashTagStatisticList.size(); i++) {
            amount += hashTagStatisticList.get(i).getTotalPeriodicAmount();
        }
        return amount;
    }

    private void setIconImage(ImageView image, int imageId) {
        //TODO make function to pair data id with drawable id CODE RED
        switch (imageId) {
            case 0:
                image.setImageResource((R.mipmap.ic_cat_everything_else_big));
                break;
            case 1:
                image.setImageResource((R.mipmap.ic_cat_automotive_big));
                break;
            case 2:
                image.setImageResource((R.mipmap.ic_cat_clothing_big));
                break;
            case 3:
                image.setImageResource((R.mipmap.ic_cat_health_big));
                break;
            case 4:
                image.setImageResource((R.mipmap.ic_cat_kitchen_dining_big));
                break;
            case 5:
                image.setImageResource((R.mipmap.ic_cat_clothing_big));
                break;
            case 6:
                image.setImageResource((R.mipmap.ic_cat_health_big));
                break;
            case 7:
                image.setImageResource((R.mipmap.ic_cat_kitchen_dining_big));
                break;
            default:
                image.setImageResource((R.mipmap.ic_cat_everything_else_big));
                break;
        }
    }
}
