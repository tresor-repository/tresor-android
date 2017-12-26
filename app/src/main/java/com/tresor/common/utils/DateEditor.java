package com.tresor.common.utils;

import android.content.Context;

import com.tresor.R;


/**
 * Created by kris on 9/7/17. Tokopedia
 */

public class DateEditor {

    public static String editMonthSimplified(Context context, int month) {
        String[] monthList = context.getResources().getStringArray(R.array.simplified_months);
        return monthList[month];
    }

    public static String editMonth(Context context, int month) {
        String[] monthList = context.getResources().getStringArray(R.array.months);
        return monthList[month];
    }

    public static String dayMonthNameYearFormatter(String date, String month, String year) {
        return date + " " + month + " " + year;
    }

    public static String daySimpleMonthNameFormatter(String date, String month) {
        return date + " : " + month + " ";
    }
}
