package com.result.isoftscore.fragment

import android.support.v4.app.Fragment
import android.view.View

class TeamListParentFragment: Fragment() {
    private lateinit var rootView: View

    companion object {
        fun newInstance(): TeamListParentFragment {
            return TeamListParentFragment()
        }
    }


}