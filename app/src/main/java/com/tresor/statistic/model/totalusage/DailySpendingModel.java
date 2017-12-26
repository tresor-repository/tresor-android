package com.tresor.statistic.model.totalusage;

/**
 * Created by kris on 9/5/17. Tokopedia
 */

public class DailySpendingModel {

    private String date;

    private float amount;

    private float sum;

    private String amountString;

    private String sumString;

    private String notes;

    private String id;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public float getSum() {
        return sum;
    }

    public void setSum(float sum) {
        this.sum = sum;
    }

    public String getAmountString() {
        return amountString;
    }

    public void setAmountString(String amountString) {
        this.amountString = amountString;
    }

    public String getSumString() {
        return sumString;
    }

    public void setSumString(String sumString) {
        this.sumString = sumString;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
