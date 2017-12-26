package com.tresor.home.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.tresor.R;
import com.tresor.home.model.HashTagStatisticModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kris on 6/1/17. Tokopedia
 */

public class StatisticFlipperAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<HashTagStatisticModel> hashTagStatisticModelList;

    public StatisticFlipperAdapter(Context context, List<HashTagStatisticModel> listHashTagStatistic) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        hashTagStatisticModelList = listHashTagStatistic;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        GraphViewHolder holder;
        if(convertView == null) {
            holder = new GraphViewHolder();
            if(position == 0) {
                convertView = inflater.inflate(R.layout.view_pie_chart, parent, false);
                holder.hashTagPieChart = (PieChart) convertView
                        .findViewById(R.id.hashtag_pie_chart);
                PieDataSet pieDataSet = new PieDataSet(getPercentageAmount(), "Percentage of Hashtags");
                pieDataSet.setColors(colorList());
                PieData pieData = new PieData(pieDataSet);
                holder.hashTagPieChart.getLegend()
                        .setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
                holder.hashTagPieChart.getDescription().setEnabled(false);
                holder.hashTagPieChart.setDrawCenterText(true);
                holder.hashTagPieChart.setData(pieData);
                holder.hashTagPieChart.invalidate();
            } else if(position == 1) {
                convertView = inflater.inflate(R.layout.view_line_chart, parent, false);
                holder.hashTagLineChart = (LineChart) convertView.findViewById(R.id.hashtag_line_chart);
                holder.hashTagLineChart.setDrawGridBackground(false);
                LineData lineData = new LineData(multipleHashtagLine());
                holder.hashTagLineChart.setData(lineData);
                holder.hashTagLineChart.invalidate();
            } else if(position == 2) {
                convertView = inflater.inflate(R.layout.view_line_chart, parent, false);
                holder.hashTagLineChart = (LineChart) convertView.findViewById(R.id.hashtag_line_chart);
                holder.hashTagLineChart.setDrawGridBackground(false);
                LineDataSet lineDataSet = new LineDataSet(getLineAmountHashTag(), "Amount of Money Spent");
                setDatasetMode(lineDataSet, context.getResources().getColor(R.color.pie4), true);
                LineData lineData = new LineData(lineDataSet);
                holder.hashTagLineChart.setData(lineData);
                holder.hashTagLineChart.invalidate();
            } else {
                convertView = inflater.inflate(R.layout.view_line_chart, parent, false);
                holder.hashTagLineChart = (LineChart) convertView.findViewById(R.id.hashtag_line_chart);
            }

            convertView.setTag(holder);

        } else {
            holder = (GraphViewHolder) convertView.getTag();
        }


        return convertView;
    }

    private List<Integer> colorList() {
        List<Integer> listColor = new ArrayList<>();
        listColor.add(context.getResources().getColor(R.color.pie1));
        listColor.add(context.getResources().getColor(R.color.pie2));
        listColor.add(context.getResources().getColor(R.color.pie3));
        listColor.add(context.getResources().getColor(R.color.pie4));
        listColor.add(context.getResources().getColor(R.color.pie5));
        listColor.add(context.getResources().getColor(R.color.pie6));
        return listColor;
    }

    private List<PieEntry> getPercentageAmount() {
        List<PieEntry> pieEntries = new ArrayList<>();
        double totalAmount = getTotalAmount();
        for (int i = 0; i < hashTagStatisticModelList.size(); i++) {
            float percentile = Double.valueOf(hashTagStatisticModelList.get(i).getTotalPeriodicAmount()/totalAmount).floatValue();
            PieEntry pieEntry = new PieEntry(percentile, hashTagStatisticModelList.get(i).getHashTag());
            pieEntries.add(pieEntry);
        }
        return pieEntries;
    }

    private List<Entry> getLineAmountHashTag() {
        List<Entry> lineEntries = new ArrayList<>();
        double range = 1000;
        for (int i = 0; i < 12; i++) {
            lineEntries.add(new Entry(i, (float) (Math.random() * range)));
        }
        return lineEntries;
    }

    private double getTotalAmount() {
        double amount = 0;
        for (int i = 0; i < hashTagStatisticModelList.size(); i++) {
            amount += hashTagStatisticModelList.get(i).getTotalPeriodicAmount();
        }
        return amount;
    }

    private void setDatasetMode(LineDataSet set1, int color, boolean drawFilled) {
        set1.setDrawCircles(true);
        set1.setDrawFilled(drawFilled);
        if (drawFilled) {
            set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            set1.setCubicIntensity(0.2f);
        } set1.setFillColor(color);
        set1.setLineWidth(1.8f);
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

    private List<PieEntry> getPercentage() {
        int dataSize = getTotalData();
        List<PieEntry> pieEntries = new ArrayList<>();
        for (int i = 0; i < hashTagStatisticModelList.size(); i++) {
            float percentile = hashTagStatisticModelList.get(i).getMonthlyAmountList().size()/dataSize;
            PieEntry pieEntry = new PieEntry(percentile, hashTagStatisticModelList.get(i).getHashTag());
            pieEntries.add(pieEntry);
        }
        return pieEntries;
    }

    private int getTotalData() {
        int dataSize = 0;
        for (int i = 0; i < hashTagStatisticModelList.size(); i++) {
            dataSize += hashTagStatisticModelList.get(i).getMonthlyAmountList().size();
        }
        return dataSize;
    }

    private class GraphViewHolder {
        public PieChart hashTagPieChart;
        public BarChart hashTagBarChart;
        public LineChart hashTagLineChart;
    }

    private List<ILineDataSet>  multipleHashtagLine() {
        List<ILineDataSet> lineDataSets = new ArrayList<>();
        for(int i = 0; i < 4; i++) {
            LineDataSet lineDataSet = new LineDataSet(getLineAmountHashTag(),
                    "HashTag #" + String.valueOf(i + 1));
            setDatasetMode(lineDataSet, getColor(i), false);
            lineDataSets.add(lineDataSet);
        }
        return lineDataSets;
    }

    private int getColor(int position) {
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
}
