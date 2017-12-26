package com.tresor.statistic.model.totalusage;

import java.util.List;

/**
 * Created by kris on 9/5/17. Tokopedia
 */

public class TotalSpendingModel {

    private List<DailySpendingModel> dailySpendingModels;

    public List<DailySpendingModel> getDailySpendingModels() {
        return dailySpendingModels;
    }

    public void setDailySpendingModels(List<DailySpendingModel> dailySpendingModels) {
        this.dailySpendingModels = dailySpendingModels;
    }
}
