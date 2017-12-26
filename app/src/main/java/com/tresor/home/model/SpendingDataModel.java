package com.tresor.home.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by kris on 7/1/17. Tokopedia
 */

public class SpendingDataModel implements Parcelable{

    private int totalSpending;

    private String totalSpendingString;

    private int dailyAllocation;

    private int todayAllocation;

    private String dailyAllocationString;

    private String todayAllocationString;

    private int todaySaving;

    private String todaySavingString;

    private boolean isHistory;

    private List<FinancialHistoryModel> financialHistoryModelList;

    public SpendingDataModel() {

    }

    protected SpendingDataModel(Parcel in) {
        totalSpending = in.readInt();
        totalSpendingString = in.readString();
        dailyAllocation = in.readInt();
        todayAllocation = in.readInt();
        dailyAllocationString = in.readString();
        todayAllocationString = in.readString();
        todaySaving = in.readInt();
        todaySavingString = in.readString();
        isHistory = in.readByte() != 0;
        financialHistoryModelList = in.createTypedArrayList(FinancialHistoryModel.CREATOR);
    }

    public static final Creator<SpendingDataModel> CREATOR = new Creator<SpendingDataModel>() {
        @Override
        public SpendingDataModel createFromParcel(Parcel in) {
            return new SpendingDataModel(in);
        }

        @Override
        public SpendingDataModel[] newArray(int size) {
            return new SpendingDataModel[size];
        }
    };

    public String getTotalSpendingString() {
        return totalSpendingString;
    }

    public void setTotalSpendingString(String totalSpending) {
        this.totalSpendingString = totalSpending;
    }

    public String getDailyAllocationString() {
        return dailyAllocationString;
    }

    public void setDailyAllocationString(String dailyAllocation) {
        this.dailyAllocationString = dailyAllocation;
    }

    public String getTodayAllocationString() {
        return todayAllocationString;
    }

    public void setTodayAllocationString(String todayAllocation) {
        this.todayAllocationString = todayAllocation;
    }

    public String getTodaySavingString() {
        return todaySavingString;
    }

    public void setTodaySavingString(String todaySaving) {
        this.todaySavingString = todaySaving;
    }

    public boolean isHistory() {
        return isHistory;
    }

    public void setHistory(boolean history) {
        isHistory = history;
    }

    public List<FinancialHistoryModel> getFinancialHistoryModelList() {
        return financialHistoryModelList;
    }

    public void setFinancialHistoryModelList(List<FinancialHistoryModel> financialHistoryModelList) {
        this.financialHistoryModelList = financialHistoryModelList;
    }

    public int getTotalSpending() {
        return totalSpending;
    }

    public void setTotalSpending(int totalSpending) {
        this.totalSpending = totalSpending;
    }

    public int getDailyAllocation() {
        return dailyAllocation;
    }

    public void setDailyAllocation(int dailyAllocation) {
        this.dailyAllocation = dailyAllocation;
    }

    public int getTodayAllocation() {
        return todayAllocation;
    }

    public void setTodayAllocation(int todayAllocation) {
        this.todayAllocation = todayAllocation;
    }

    public int getTodaySaving() {
        return todaySaving;
    }

    public void setTodaySaving(int todaySaving) {
        this.todaySaving = todaySaving;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(totalSpending);
        dest.writeString(totalSpendingString);
        dest.writeInt(dailyAllocation);
        dest.writeInt(todayAllocation);
        dest.writeString(dailyAllocationString);
        dest.writeString(todayAllocationString);
        dest.writeInt(todaySaving);
        dest.writeString(todaySavingString);
        dest.writeByte((byte) (isHistory ? 1 : 0));
        dest.writeTypedList(financialHistoryModelList);
    }
}
