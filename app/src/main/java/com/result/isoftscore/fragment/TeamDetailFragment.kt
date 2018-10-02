package com.result.isoftscore.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.result.isoftscore.R
import com.result.isoftscore.R.color.colorAccent
import com.result.isoftscore.activity.PlayerDetailActivity
import com.result.isoftscore.adapter.TeamDetailAdapter
import com.result.isoftscore.helper.BottomDecoration
import com.result.isoftscore.model.Player
import com.result.isoftscore.presenter.TeamDetailPresenter
import com.result.isoftscore.util.gone
import com.result.isoftscore.util.toPx
import com.result.isoftscore.view.TeamDetailView
import kotlinx.android.synthetic.main.fragment_list.view.*
import org.jetbrains.anko.support.v4.ctx

class TeamDetailFragment: Fragment(), TeamDetailView, SwipeRefreshLayout.OnRefreshListener {

    private lateinit var rootView: View
    private lateinit var presenter: TeamDetailPresenter
    private val players: MutableList<Player> = mutableListOf()
    private lateinit var adapter: TeamDetailAdapter
    private var idTeam: String? = null

    companion object {
        const val ARG_ID_TEAM = "ID_TEAM"
        fun newInstance(idTeam: String): TeamDetailFragment {
            val fragment = TeamDetailFragment()
            val args = Bundle()
            args.putString(ARG_ID_TEAM, idTeam)
            fragment.arguments = args

            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        idTeam = arguments?.getString(ARG_ID_TEAM)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_list, container, false)
        rootView.swiperefresh_list.setOnRefreshListener(this)
        rootView.swiperefresh_list.setColorSchemeResources(colorAccent,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light)
        rootView.recyclerview_list.layoutManager = LinearLayoutManager(ctx)
        rootView.recyclerview_list.addItemDecoration(BottomDecoration(16.toPx()))
        rootView.recyclerviewheader_list.gone()

        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter = TeamDetailAdapter(players) {
            PlayerDetailActivity.start(ctx, it)
        }
        rootView.recyclerview_list.adapter = adapter
        presenter = TeamDetailPresenter(this)
        presenter.getPlayerList(idTeam)
    }

    override fun onDestroyView() {
        presenter.stopPresenting()
        super.onDestroyView()
    }

    override fun showLoading() {
        rootView.swiperefresh_list.isRefreshing = true
    }

    override fun hideLoading() {
        rootView.swiperefresh_list.isRefreshing = false
    }

    override fun showPlayerList(data: List<Player>) {
        players.clear()
        players.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun onRefresh() {
        presenter.getPlayerList(idTeam)
    }

}

