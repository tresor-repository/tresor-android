package com.tresor.home.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kris on 5/28/17. Tokopedia
 */

public class FinancialHistoryModel implements Parcelable{

    private String amount;

    private double amountUnformatted;

    private boolean usesComma;

    private int currencyId;

    private List<String> hashtag = new ArrayList<>();

    private String info;

    private String date;

    private int theme;

    private String hashTagString;

    public FinancialHistoryModel() {

    }

    protected FinancialHistoryModel(Parcel in) {
        amount = in.readString();
        amountUnformatted = in.readDouble();
        usesComma = in.readByte() != 0;
        currencyId = in.readInt();
        hashtag = in.createStringArrayList();
        info = in.readString();
        date = in.readString();
        theme = in.readInt();
        hashTagString = in.readString();
    }

    public static final Creator<FinancialHistoryModel> CREATOR = new Creator<FinancialHistoryModel>() {
        @Override
        public FinancialHistoryModel createFromParcel(Parcel in) {
            return new FinancialHistoryModel(in);
        }

        @Override
        public FinancialHistoryModel[] newArray(int size) {
            return new FinancialHistoryModel[size];
        }
    };

    public String getAmount() {
        return amount;
    }

    public double getAmountUnformatted() {
        return amountUnformatted;
    }

    public void setAmountUnformatted(double amountUnformatted) {
        this.amountUnformatted = amountUnformatted;
    }

    public boolean isUsesComma() {
        return usesComma;
    }

    public void setUsesComma(boolean usesComma) {
        this.usesComma = usesComma;
    }

    public int getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(int currencyId) {
        this.currencyId = currencyId;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public List<String> getHashtag() {
        return hashtag;
    }

    public void setHashtag(List<String> hashtag) {
        this.hashtag = hashtag;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTheme() {
        return theme;
    }

    public void setTheme(int theme) {
        this.theme = theme;
    }

    public String getHashTagString() {
        String joinHashTag = "";
        if(hashtag !=null && hashtag.size() > 0) {
            for (int i = 0; i<hashtag.size(); i++) {
                joinHashTag += hashtag.get(i);
            }
        }
        return joinHashTag;
    }

    public void setHashTagString(String hashTagString) {
        this.hashTagString = hashTagString;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(amount);
        dest.writeDouble(amountUnformatted);
        dest.writeByte((byte) (usesComma ? 1 : 0));
        dest.writeInt(currencyId);
        dest.writeStringList(hashtag);
        dest.writeString(info);
        dest.writeString(date);
        dest.writeInt(theme);
        dest.writeString(hashTagString);
    }
}
