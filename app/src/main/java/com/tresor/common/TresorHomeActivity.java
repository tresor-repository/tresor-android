package com.tresor.common;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.tresor.R;
import com.tresor.common.activity.TresorPlainActivity;

/**
 * Created by kris on 6/22/17. Tokopedia
 */

public class TresorHomeActivity extends TresorPlainActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

    }
}
