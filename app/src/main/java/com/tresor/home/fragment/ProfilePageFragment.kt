package com.tresor.home.fragment

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tresor.R
import kotlinx.android.synthetic.main.profile_page_fragment.*

/**
 * Created by kris on 5/28/18. Tokopedia
 */
class ProfilePageFragment: Fragment() {

    companion object {
        fun createInstance(): ProfilePageFragment {
            return ProfilePageFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.profile_page_fragment, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}