package com.tresor.common;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.tresor.R;
import com.tresor.home.fragment.ListFinancialHistoryFragment;
import com.tresor.home.fragment.SearchFragment;
import com.tresor.home.fragment.ChartFragment;
import com.tresor.home.fragment.StatisticFragment;

/**
 * Created by kris on 6/11/17. Tokopedia
 */

public abstract class TresorActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.home_bottom_navigation_view);
        bottomNavigationView.inflateMenu(R.menu.home_bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener());
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener() {
        return new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.add_menu:
                        selectedFragment = ListFinancialHistoryFragment.createFragment();
                        break;
                    case R.id.search_menu:
                        selectedFragment = SearchFragment.createInstance();
                        break;
                    case R.id.statistic_menu:
                        selectedFragment = StatisticFragment.Companion.createInstance();
                        break;
                }
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.home_root_view, selectedFragment);
                ft.commit();
                return true;
            }
        };
    }

    protected void setSelectedMenu(int selectedMenu) {
        bottomNavigationView.setSelectedItemId(selectedMenu);
    }

}
