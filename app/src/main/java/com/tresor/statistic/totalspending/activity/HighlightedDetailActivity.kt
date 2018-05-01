package com.tresor.statistic.totalspending.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle

import com.tresor.R
import com.tresor.common.activity.TresorPlainActivity
import com.tresor.home.fragment.ListFinancialHistoryFragment


/**
 * Created by kris on 9/5/17. Tokopedia
 */

fun Context.highlightedDetailIntent(pageId: String): Intent {
    return Intent(this, HighlightedDetailActivity::class.java).apply {
        putExtra(PAGE_ID, pageId)
    }
}

private const val PAGE_ID = "PAGE_ID"

class HighlightedDetailActivity : TresorPlainActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.highlight_detail_layout)
        fragmentManager
                .beginTransaction()
                .replace(R.id.highlighted_detail_main_view,
                        ListFinancialHistoryFragment.createFragment())
                .commit()
    }
}
