package com.tresor.home.inteface;

import com.tresor.home.model.FinancialHistoryModel;

/**
 * Created by kris on 7/10/17. Tokopedia
 */

public interface NewDataAddedListener {

    void onDataAdded(FinancialHistoryModel newData);

}
