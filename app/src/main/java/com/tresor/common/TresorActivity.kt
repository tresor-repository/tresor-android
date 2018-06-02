package com.tresor.common

import android.annotation.SuppressLint
import android.app.Fragment
import android.os.Bundle
import android.support.design.internal.BottomNavigationMenuView
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity

import com.tresor.R
import com.tresor.home.fragment.ProfilePageFragment
import com.tresor.home.fragment.TodaySpendingFragment
import com.tresor.home.fragment.SearchFragmentKotlin
import com.tresor.home.fragment.StatisticFragment
import android.support.design.internal.BottomNavigationItemView



/**
 * Created by kris on 6/11/17. Tokopedia
 */

abstract class TresorActivity : AppCompatActivity() {

    private var bottomNavigationView: BottomNavigationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)
        bottomNavigationView = findViewById(R.id.home_bottom_navigation_view) as BottomNavigationView
        bottomNavigationView!!.inflateMenu(R.menu.home_bottom_navigation)
        bottomNavigationView!!.setOnNavigationItemSelectedListener(navListener())
        disableAnnoyingBottomNavAnimation()
    }

    private fun navListener(): BottomNavigationView.OnNavigationItemSelectedListener {
        return BottomNavigationView.OnNavigationItemSelectedListener { item ->
            var selectedFragment: Fragment? = null
            when (item.itemId) {
                R.id.add_menu -> selectedFragment = TodaySpendingFragment.createFragment()
                R.id.search_menu -> selectedFragment = SearchFragmentKotlin.createInstance()
                R.id.statistic_menu -> selectedFragment = StatisticFragment.createInstance()
                R.id.profile_menu -> selectedFragment = ProfilePageFragment.createInstance()
            }
            val ft = fragmentManager.beginTransaction()
            ft.replace(R.id.home_root_view, selectedFragment)
            ft.commit()
            true
        }
    }

    @SuppressLint("RestrictedApi")
    private fun disableAnnoyingBottomNavAnimation() {
        val menuView: BottomNavigationMenuView = bottomNavigationView!!.getChildAt(0) as BottomNavigationMenuView
        val shiftingMode = menuView.javaClass.getDeclaredField("mShiftingMode")
        shiftingMode.isAccessible = true
        shiftingMode.setBoolean(menuView, false)
        shiftingMode.isAccessible = false
        for (i in 0 until menuView.getChildCount()) {
            val item = menuView.getChildAt(i) as BottomNavigationItemView

            item.setShiftingMode(false)
            // set once again checked value, so view will be updated

            item.setChecked(item.itemData.isChecked)
        }
    }

    protected fun setSelectedMenu(selectedMenu: Int) {
        bottomNavigationView!!.selectedItemId = selectedMenu
    }

}
