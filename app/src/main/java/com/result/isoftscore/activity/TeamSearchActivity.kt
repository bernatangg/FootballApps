package com.result.isoftscore.activity

import android.content.Context
import android.support.v7.app.AppCompatActivity
import com.result.isoftscore.adapter.TeamListAdapter
import com.result.isoftscore.model.Team
import com.result.isoftscore.presenter.TeamSearchPresenter
import com.result.isoftscore.view.TeamSearchView
import org.jetbrains.anko.startActivity

class TeamSearchActivity: AppCompatActivity(), TeamSearchView {

    private val teams: MutableList<Team> = mutableListOf()
    private lateinit var presenter: TeamSearchPresenter
    private lateinit var listAdapter: TeamListAdapter
    private lateinit var searchView: TeamSearchView
    companion object {
        fun start(context: Context) {
            context.startActivity<TeamSearchActivity>()
        }
    }

    override fun showLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showTeamsFOund(data: List<Team>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}

