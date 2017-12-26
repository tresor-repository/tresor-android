package com.tresor.home.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ViewFlipper;

import com.tresor.R;
import com.tresor.common.utils.DateEditor;
import com.tresor.home.model.HashTagStatisticModel;
import com.tresor.statistic.HashTagPieChart;
import com.tresor.statistic.HashTagUsageLineChart;
import com.tresor.statistic.TotalUsageLineChart;
import com.tresor.statistic.dialog.TimePickerDialogFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by kris on 6/1/17. Tokopedia
 */

public class ChartFragment extends Fragment {

    private ViewFlipper statisticFlipper;

    private Spinner graphSelector;

    private ArrayAdapter spinnerAdapter;

    private HashTagPieChart hashTagPieChart;

    private HashTagUsageLineChart hashTagUsageLineChart;

    private TotalUsageLineChart totalUsageLineChart;

    private EditText startDateField;

    private EditText endDateField;

    public static ChartFragment createStatisticFragment() {
        return new ChartFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_statistic, container, false);
        statisticFlipper = (ViewFlipper) view.findViewById(R.id.statistic_flipper);
        graphSelector = (Spinner) view.findViewById(R.id.graph_selector_spinner);
        spinnerAdapter = new ArrayAdapter(getActivity(),
                android.R.layout.simple_spinner_dropdown_item,
                spinnerChoices());
        hashTagPieChart = (HashTagPieChart)
                view.findViewById(R.id.hashtag_pie_chart_layout);
        hashTagUsageLineChart = (HashTagUsageLineChart)
                view.findViewById(R.id.hashtag_usage_line_chart_layout);
        totalUsageLineChart = (TotalUsageLineChart)
                view.findViewById(R.id.total_usage_line_chart_layout);
        graphSelector.setAdapter(spinnerAdapter);
        graphSelector.setOnItemSelectedListener(onSpinnerItemSelectedListener());
        startDateField = (EditText) view.findViewById(R.id.start_date_field);
        startDateField.setOnClickListener(onStartDateClickedListener());
        endDateField = (EditText) view.findViewById(R.id.end_date_field);
        endDateField.setOnClickListener(onEndDateClickedListener());
        initiateData();
        return view;
    }

    private void initiateData() {
        //TODO Get initial Data. Called after successful API call.
        //TODO Default initialization, monthly data from date 1 to today's date
        String currentDate = String.valueOf(Calendar.getInstance().get(Calendar.DATE));
        String currentMonth = DateEditor
                .editMonth(getActivity(), Calendar.getInstance().get(Calendar.MONTH));
        String currentYear = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        startDateField.setText(DateEditor
                .dayMonthNameYearFormatter("1", currentMonth, currentYear));
        endDateField.setText(DateEditor
                .dayMonthNameYearFormatter(currentDate, currentMonth, currentYear));
        hashTagPieChart.setChartData(getActivity(), hashTagStatisticModelList());
        hashTagUsageLineChart.setLineChart(getActivity(), hashTagStatisticModelList());
        totalUsageLineChart.setData(getActivity());
    }

    private List<HashTagStatisticModel> hashTagStatisticModelList() {
        List<HashTagStatisticModel> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            HashTagStatisticModel hashTagStatisticModel = new HashTagStatisticModel();
            switch (i) {
                case 0:
                    hashTagStatisticModel.setHashTag("#makan");
                    hashTagStatisticModel.setIdHashTag("0");
                    break;
                case 1:
                    hashTagStatisticModel.setHashTag("#malem");
                    hashTagStatisticModel.setIdHashTag("1");
                    break;
                case 2:
                    hashTagStatisticModel.setHashTag("#KFC");
                    hashTagStatisticModel.setIdHashTag("2");
                    break;
                case 3:
                    hashTagStatisticModel.setHashTag("#bolang");
                    hashTagStatisticModel.setIdHashTag("3");
                    break;
                default:
                    hashTagStatisticModel.setHashTag("Others");
                    break;
            }
            hashTagStatisticModel.setInformationList(dummyInformation(i));
            hashTagStatisticModel.setMonthlyAmountList(dummyAmount(i));
            hashTagStatisticModel.setTotalPeriodicAmount((double)((i+1) * 1000));
            list.add(hashTagStatisticModel);
        }
        return list;
    }

    private List<String> dummyInformation(int numberOfDummies) {
        List<String> dummy = new ArrayList<>();
        for (int i = 0; i < numberOfDummies + 2; i++) {
            dummy.add("Dummy Info");
        }
        return dummy;
    }

    private List<Double> dummyAmount(int dummyModifier) {
        List<Double> dummy = new ArrayList<>();
        for (int i = 0; i< dummyModifier + 2; i++) {
            dummy.add((double) 10000 * dummyModifier);
        }
        return dummy;
    }

    private AdapterView.OnItemSelectedListener onSpinnerItemSelectedListener() {
        return new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    hashTagPieChart.setVisibility(View.GONE);
                    totalUsageLineChart.setVisibility(View.VISIBLE);
                    hashTagUsageLineChart.setVisibility(View.GONE);
                } else if (position == 1) {
                    hashTagPieChart.setVisibility(View.GONE);
                    totalUsageLineChart.setVisibility(View.GONE);
                    hashTagUsageLineChart.setVisibility(View.VISIBLE);
                } else if (position == 2) {
                    hashTagPieChart.setVisibility(View.VISIBLE);
                    totalUsageLineChart.setVisibility(View.GONE);
                    hashTagUsageLineChart.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
    }

    private ArrayList<String> spinnerChoices() {
        ArrayList<String> choiceList = new ArrayList<>();
        choiceList.add("Total Spending");
        choiceList.add("HashTag Comparison");
        choiceList.add("Expense By Hashtag");
        return choiceList;
    }

    private View.OnClickListener onStartDateClickedListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialogFragment pickerDialogFragment = TimePickerDialogFragment
                        .openTimerDialog(TimePickerDialogFragment.START_DATE_MODE);
                pickerDialogFragment.show(getFragmentManager(), "timePicker");
            }
        };
    }

    private View.OnClickListener onEndDateClickedListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialogFragment pickerDialogFragment = TimePickerDialogFragment
                        .openTimerDialog(TimePickerDialogFragment.END_DATE_MODE);
                pickerDialogFragment.show(getFragmentManager(), "timePicker");
            }
        };
    }

    public void onDateSelected(int mode, int year, String month, int dayOfMonth) {
        //TODO send data to WS once done put these in listener
        String pickedDate = DateEditor.dayMonthNameYearFormatter(String.valueOf(dayOfMonth),
                month, String.valueOf(year));
        switch (mode) {
            case TimePickerDialogFragment.START_DATE_MODE:
                startDateField.setText(pickedDate);
                break;
            case TimePickerDialogFragment.END_DATE_MODE:
                endDateField.setText(pickedDate);
                break;
            default:
                startDateField.setText("");
                endDateField.setText("");
                break;

        }
        changeRange();
    }

    private void changeRange() {
        if(!startDateField.getText().toString().equals("")
                && !endDateField.getText().toString().equals("")) {
            hashTagPieChart.setChartData(getActivity(), hashTagStatisticModelList());
            hashTagUsageLineChart.setLineChart(getActivity(), hashTagStatisticModelList());
            totalUsageLineChart.setData(getActivity());
        }
    }

    public void receivedHashTagComparisonData(List<String> listHashTagToCompare) {
        hashTagUsageLineChart.receivedListOfHashTagComparison(listHashTagToCompare);
    }
}
