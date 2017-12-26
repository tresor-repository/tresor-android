package com.tresor.statistic.totalspending.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.tresor.R;
import com.tresor.common.activity.TresorPlainActivity;
import com.tresor.home.fragment.ListFinancialHistoryFragment;


/**
 * Created by kris on 9/5/17. Tokopedia
 */

public class HighlightedDetailActivity extends TresorPlainActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.highlight_detail_layout);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.highlighted_detail_main_view,
                        ListFinancialHistoryFragment.createFragment())
                .commit();
    }
}
