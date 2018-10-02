package com.result.isoftscore.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.result.isoftscore.R
import com.result.isoftscore.fragment.MatchListParentFragment
import com.result.isoftscore.fragment.TeamListFragment
import com.result.isoftscore.fragment.TeamListParentFragment
import com.result.isoftscore.util.replaceFragmentSafely
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        main_bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_nav_match -> {
                    loadMatchFragment(savedInstanceState)
                }
                R.id.bottom_nav_teams -> {
                    loadTeamsFragment(savedInstanceState)
                }
            }
            true
        }
        main_bottom_navigation.selectedItemId = R.id.bottom_nav_match
    }

    private fun loadMatchFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            replaceFragmentSafely(
                    fragment = MatchListParentFragment.newInstance(),
                    tag = MatchListParentFragment::class.java.simpleName,
                    containerViewId = R.id.main_container)
        }
    }

    private fun loadTeamsFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            replaceFragmentSafely(
                    fragment = TeamListParentFragment.newInstance(),
                    tag = TeamListFragment::class.java.simpleName,
                    containerViewId = R.id.main_container
            )
        }
    }
}
