package com.result.isoftscore.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.*
import com.result.isoftscore.R
import com.result.isoftscore.activity.TeamSearchActivity
import com.result.isoftscore.adapter.BasePageAdapter
import com.result.isoftscore.helper.TeamListType
import kotlinx.android.synthetic.main.fragment_list_parent.view.*
import org.jetbrains.anko.support.v4.ctx

class TeamListParentFragment: Fragment() {
    private lateinit var rootView: View

    companion object {
        fun newInstance(): TeamListParentFragment {
            return TeamListParentFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_list_parent, container, false)

        setupToolbar()
        setupViewPager()

        return rootView
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.list_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.action_open_search_activity -> {
                TeamSearchActivity.start(ctx)
                true
            } else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(rootView.toolbar_list)
    }

    private fun setupViewPager() {
        val pagerAdapter = BasePageAdapter(childFragmentManager)
        pagerAdapter.addFragment(TeamListFragment.newInstance(TeamListType.LIST_TEAM), getString(R.string.team_list))
        pagerAdapter.addFragment(TeamListFragment.newInstance(TeamListType.FAVORITES_TEAM), getString(R.string.favorite))
        rootView.viewpager_list.adapter = pagerAdapter
        rootView.tablayout_list.setupWithViewPager(rootView.viewpager_list)
    }
}