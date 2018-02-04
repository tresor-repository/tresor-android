package com.tresor.home.fragment

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tresor.R

/**
 * Created by kris on 1/8/18. Tokopedia
 */

public class TodaySpendingFragment : Fragment(){

    companion object {
        fun createInstance():TodaySpendingFragment{
            return TodaySpendingFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        /*val mainView = inflater.inflate(R.layout.fragment_list_financial_history,
                container, false);*/
        return super.onCreateView(inflater, container, savedInstanceState)
    }

}