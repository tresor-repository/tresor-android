package com.tresor.statistic

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.tresor.R
import com.tresor.common.activity.TresorPlainActivity
import com.tresor.home.fragment.TodaySpendingFragment

/**
 * Created by kris on 5/1/18. Tokopedia
 */

fun Context.hashtagHistoryIntent(hashTag: String, dateStart: String, dateEnd: String): Intent {
    return Intent(this, HashTagHistoryActivity::class.java).apply {
        val bundle = Bundle()
        bundle.apply {
            putString(HASH_TAG, hashTag)
            putString(DATE_START, dateStart)
            putString(DATE_END, dateEnd)
        }
        putExtras(Bundle())
    }
}

private const val HASH_TAG = "HASH_TAG"
private const val DATE_START = "DATE_START"
private const val DATE_END = "DATE_END"

class HashTagHistoryActivity : TresorPlainActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.highlight_detail_layout)
        fragmentManager
                .beginTransaction()
                .replace(R.id.highlighted_detail_main_view,
                        TodaySpendingFragment.createFragment())
                .commit()
    }

}