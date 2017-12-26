package com.tresor.home.model;

import java.util.List;

/**
 * Created by kris on 6/1/17. Tokopedia
 */

public class HashTagStatisticModel {

    private String idHashTag;

    private String hashTag;

    private List<Double> monthlyAmountList;

    private List<String> periodicAmoutList;

    private List<String> informationList;

    private Double totalPeriodicAmount;

    public String getIdHashTag() {
        return idHashTag;
    }

    public void setIdHashTag(String idHashTag) {
        this.idHashTag = idHashTag;
    }

    public String getHashTag() {
        return hashTag;
    }

    public void setHashTag(String hashTag) {
        this.hashTag = hashTag;
    }

    public List<Double> getMonthlyAmountList() {
        return monthlyAmountList;
    }

    public void setMonthlyAmountList(List<Double> monthlyAmountList) {
        this.monthlyAmountList = monthlyAmountList;
    }

    public List<String> getInformationList() {
        return informationList;
    }

    public void setInformationList(List<String> informationList) {
        this.informationList = informationList;
    }

    public List<String> getPeriodicAmoutList() {
        return periodicAmoutList;
    }

    public void setPeriodicAmoutList(List<String> periodicAmoutList) {
        this.periodicAmoutList = periodicAmoutList;
    }

    public Double getTotalPeriodicAmount() {
        return totalPeriodicAmount;
    }

    public void setTotalPeriodicAmount(Double totalPeriodicAmount) {
        this.totalPeriodicAmount = totalPeriodicAmount;
    }
}
