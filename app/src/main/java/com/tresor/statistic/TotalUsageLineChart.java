package com.tresor.statistic;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.tresor.R;
import com.tresor.statistic.totalspending.activity.HighlightedDetailActivity;
import com.tresor.statistic.model.totalusage.DailySpendingModel;
import com.tresor.statistic.model.totalusage.TotalSpendingModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kris on 9/2/17. Tokopedia
 */

public class TotalUsageLineChart extends LinearLayout {

    private LineChart hashTagLineChart;
    private TextView highlightedSpending;
    private TextView goToDetailButton;

    public TotalUsageLineChart(Context context) {
        super(context);
        initView(context);
    }

    public TotalUsageLineChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public TotalUsageLineChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view =  inflater.inflate(R.layout.view_line_chart, this, true);
        hashTagLineChart = (LineChart) view.findViewById(R.id.hashtag_line_chart);
        highlightedSpending = (TextView) view.findViewById(R.id.highlighted_spending);
        goToDetailButton = (TextView) view.findViewById(R.id.go_to_detail_button);
        hashTagLineChart.getAxisLeft().setDrawGridLines(false);
        hashTagLineChart.getAxisLeft().setDrawLabels(false);
        hashTagLineChart.getXAxis().setDrawGridLines(false);
        hashTagLineChart.getLegend().setEnabled(false);
        hashTagLineChart.setDrawGridBackground(false);
        hashTagLineChart.getAxisRight().setDrawGridLines(false);
        hashTagLineChart.getDescription().setTextSize(16);
    }

    //TODO Kalo dah ga dummy kasih data di set data
    public void setData(Context context) {
        TotalSpendingModel totalSpendingModel = dummyTotalSpendingModel();
        LineDataSet lineDataSet = new LineDataSet(getLineAmountHashTag(totalSpendingModel), "Amount of Money Spent");
        setDatasetMode(lineDataSet, context.getResources().getColor(R.color.pie4), true);
        LineData lineData = new LineData(lineDataSet);
        lineData.setValueTextSize(10);
        lineData.setDrawValues(false);
        hashTagLineChart.getXAxis().setValueFormatter(dateValueFormatter(totalSpendingModel));
        hashTagLineChart.setData(lineData);
        hashTagLineChart.animateX(1000);
        DailySpendingModel latestIndexData = totalSpendingModel.getDailySpendingModels()
                .get(totalSpendingModel.getDailySpendingModels().size()-1);
        highlightedSpending.setText(String.valueOf(latestIndexData.getDate())
                + " : "
                + String.valueOf(latestIndexData.getAmount()));
        hashTagLineChart
                .setOnChartValueSelectedListener(onChartValueSelectedListener(
                        context, totalSpendingModel
                ));
        goToDetailButton.setOnClickListener(onGoToDetailButtonListener(
                latestIndexData.getId())
        );
        hashTagLineChart.getDescription().setText("Total Spending: " +
                String.valueOf(latestIndexData.getSum()));
    }

    private List<Entry> getLineAmountHashTag(TotalSpendingModel totalSpendingModel) {
        List<DailySpendingModel> dailySpendingList = totalSpendingModel.getDailySpendingModels();
        List<Entry> lineEntries = new ArrayList<>();
        for (int i = 0; i < dailySpendingList.size(); i++) {
            lineEntries.add(new Entry(i, dailySpendingList.get(i).getSum()));
        }
        return lineEntries;
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
        //set1.setHighLightColor(Color.rgb(244, 117, 117));
        set1.setDrawCircleHole(false);
        set1.setHighlightLineWidth(1.5f);
        set1.setHighLightColor(color);
        set1.setColor(color);
        set1.setFillAlpha(100);
        set1.setDrawHorizontalHighlightIndicator(true);
        set1.setFillFormatter(new IFillFormatter() {
            @Override
            public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                return -10;
            }
        });
    }

    private OnChartValueSelectedListener onChartValueSelectedListener(final Context context,
                                                                      final TotalSpendingModel totalSpendingModel) {
        return new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                DailySpendingModel selectedSpending = totalSpendingModel.getDailySpendingModels()
                        .get(
                        Math.round(e.getX()));
                highlightedSpending.setText(String
                        .valueOf(selectedSpending.getDate())
                        + " : " +
                        String.valueOf(selectedSpending.getAmount()));
                goToDetailButton.setOnClickListener(onGoToDetailButtonListener(
                        selectedSpending.getId()));
            }

            @Override
            public void onNothingSelected() {
                List<DailySpendingModel> listOfSpendingModel = totalSpendingModel
                        .getDailySpendingModels();
                DailySpendingModel latestSpendingModel = listOfSpendingModel
                        .get(listOfSpendingModel.size()-1);
                highlightedSpending.setText(latestSpendingModel.getDate()
                        + " : "
                        + latestSpendingModel.getAmount()
                        );
                goToDetailButton
                        .setOnClickListener(onGoToDetailButtonListener(
                                latestSpendingModel.getId()));
            }
        };
    }

    private IAxisValueFormatter dateValueFormatter(final TotalSpendingModel totalSpendingModel) {
        return new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return totalSpendingModel.getDailySpendingModels().get(Math.round(value)).getDate();
            }
        };
    }

    private TotalSpendingModel dummyTotalSpendingModel() {
        TotalSpendingModel totalSpendingModel = new TotalSpendingModel();
        List<DailySpendingModel> dummyDailySpendingModel = new ArrayList<>();
        for(int i = 0; i<12; i++) {
            DailySpendingModel dailySpendingModel = new DailySpendingModel();
            //TODO Later not random, use real data
            dailySpendingModel.setAmount((float)Math.random() * 10000);
            if(i>0) {
                dailySpendingModel.setSum(dummyDailySpendingModel.get(i-1).getSum()
                        + dailySpendingModel.getAmount());
            } else dailySpendingModel.setSum(dailySpendingModel.getAmount());
            dailySpendingModel.setDate(String.valueOf(i+1) +" Dec");
            dummyDailySpendingModel.add(dailySpendingModel);
            dailySpendingModel.setId(String.valueOf(i)+"id");
        }
        totalSpendingModel.setDailySpendingModels(dummyDailySpendingModel);
        return totalSpendingModel;
    }

    private OnClickListener onGoToDetailButtonListener(final String pageId) {
        return new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), HighlightedDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("page_id", pageId);
                intent.putExtras(bundle);
                getContext().startActivity(intent);
            }
        };
    }
}
