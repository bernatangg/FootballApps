package com.result.isoftscore.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.result.isoftscore.R
import com.result.isoftscore.R.color.colorAccent
import com.result.isoftscore.activity.TeamDetailActivity
import com.result.isoftscore.adapter.TeamListAdapter
import com.result.isoftscore.helper.BottomDecoration
import com.result.isoftscore.helper.LeagueData
import com.result.isoftscore.helper.TeamListType
import com.result.isoftscore.model.League
import com.result.isoftscore.model.Team
import com.result.isoftscore.presenter.TeamListPresenter
import com.result.isoftscore.util.gone
import com.result.isoftscore.util.toPx
import com.result.isoftscore.view.TeamListView
import kotlinx.android.synthetic.main.fragment_list.view.*
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.support.v4.ctx

class TeamListFragment: Fragment(), TeamListView, SwipeRefreshLayout.OnRefreshListener {

    private lateinit var rootView: View
    private lateinit var presenter: TeamListPresenter
    private val teams: MutableList<Team> = mutableListOf()
    private lateinit var adapter: TeamListAdapter
    private lateinit var teamListType: TeamListType
    private var leagueId: String? = null

    companion object {
        const val ARG_TEAM_LIST_TYPE = "TEAM_LIST_TYPE"
        fun newInstance(teamListType: TeamListType): TeamListFragment {
            val fragment = TeamListFragment()
            val args = Bundle()
            args.putSerializable(ARG_TEAM_LIST_TYPE, teamListType)
            fragment.arguments = args

            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        teamListType = arguments?.getSerializable(ARG_TEAM_LIST_TYPE) as TeamListType
        if (teamListType == TeamListType.FAVORITES_TEAM)
            setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_list, container, false)
        rootView.swiperefresh_list.setOnRefreshListener(this)
        rootView.swiperefresh_list.setColorSchemeResources(colorAccent,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light)
        rootView.recyclerview_list.contentDescription = recyclerViewContentDescription(teamListType)
        rootView.recyclerview_list.layoutManager = LinearLayoutManager(ctx)

        val teamListPadding = 32.toPx()
        rootView.recyclerview_list.addItemDecoration(BottomDecoration(teamListPadding))
        rootView.recyclerview_list.setPadding(teamListPadding, teamListPadding, teamListPadding, teamListPadding)

        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter = TeamListAdapter(teams) {
            TeamDetailActivity.start(ctx, it)
        }
        rootView.recyclerview_list.adapter = adapter
        if (teamListType != TeamListType.FAVORITES_TEAM) {
            setupRecyclerViewHeader()
        } else {
            rootView.recyclerviewheader_list.gone()
        }
        presenter = TeamListPresenter(context, this)

    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.fav_list_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.action_remove_all_favorites -> {
                alert ("Remove all favorites team?") {
                    positiveButton("Yes") {
                        if (!teams.isEmpty()) presenter.removeAllFavoritesTeam()
                    }
                    negativeButton("No") {}
                }.show()
                true
            } else -> super.onOptionsItemSelected(item)
        }
    }

    private fun recyclerViewContentDescription(teamListType: TeamListType): String {
        return when (teamListType) {
            TeamListType.LIST_TEAM -> getString(R.string.all_team)
            TeamListType.FAVORITES_TEAM -> getString(R.string.all_fav_team)
        }
    }

    private fun setupRecyclerViewHeader() {
        rootView.recyclerviewheader_list.attachTo(rootView.recyclerview_list)

        val spinnerAdapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, LeagueData.provideLeagueData())
        rootView.spinner_list.adapter = spinnerAdapter
        rootView.spinner_list.onItemSelectedListener = object  : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedLeague: League = rootView.spinner_list.selectedItem as League
                leagueId = selectedLeague.leagueId
                presenter.getTeamList(teamListType, leagueId)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (teamListType == TeamListType.FAVORITES_TEAM) {
            presenter.getTeamList(teamListType, leagueId)
        }
    }

    override fun onDestroy() {
        presenter.stopPresenting()
        super.onDestroy()
    }

    override fun showLoading() {
        rootView.swiperefresh_list.isRefreshing = true
    }

    override fun hideLoading() {
        rootView.swiperefresh_list.isRefreshing = false
    }

    override fun showTeamList(data: List<Team>) {
        teams.clear()
        teams.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun onRefresh() {
        presenter.getTeamList(teamListType, leagueId)
    }

}