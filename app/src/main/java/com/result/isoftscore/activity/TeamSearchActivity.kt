package com.result.isoftscore.activity

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import com.result.isoftscore.R
import com.result.isoftscore.adapter.TeamListAdapter
import com.result.isoftscore.helper.BottomDecoration
import com.result.isoftscore.model.Team
import com.result.isoftscore.presenter.TeamSearchPresenter
import com.result.isoftscore.util.gone
import com.result.isoftscore.util.nullToEmpty
import com.result.isoftscore.util.toPx
import com.result.isoftscore.util.visible
import com.result.isoftscore.view.TeamSearchView
import kotlinx.android.synthetic.main.activity_search.*
import org.jetbrains.anko.ctx
import org.jetbrains.anko.startActivity

class TeamSearchActivity: AppCompatActivity(), TeamSearchView {

    private val teams: MutableList<Team> = mutableListOf()
    private lateinit var presenter: TeamSearchPresenter
    private lateinit var listAdapter: TeamListAdapter
    private lateinit var searchView: SearchView
    companion object {
        fun start(context: Context) {
            context.startActivity<TeamSearchActivity>()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        setupToolbar()
        setupRecyclerView()

        presenter = TeamSearchPresenter(this)
    }

    private fun setupToolbar() {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = getString(R.string.search_team)
        }
    }

    private fun setupRecyclerView() {
        listAdapter = TeamListAdapter(teams) {
            TeamDetailActivity.start(this, it)
        }
        val teamListPadding = 32.toPx()
        recyclerview_search.apply {
            layoutManager = LinearLayoutManager(ctx)
            addItemDecoration(BottomDecoration(teamListPadding))
            setPadding(teamListPadding, teamListPadding,teamListPadding, teamListPadding)
            adapter = listAdapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search, menu)
        val menuSearch = menu?.findItem(R.id.action_search)
        searchView = menuSearch?.actionView as SearchView

        setupSearchVIew()

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            } else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupSearchVIew() {
        searchView.queryHint = getString(R.string.search)
        searchView.isIconified = false
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean { return false }

            override fun onQueryTextChange(newText: String?): Boolean {
                presenter.searchTeam(newText.nullToEmpty())
                return true
            }
        })
    }

    override fun onDestroy() {
        presenter.stopPresenting()
        super.onDestroy()
    }

    override fun showLoading() {
        progressbar_search.visible()
    }

    override fun hideLoading() {
        progressbar_search.gone()
    }

    override fun showTeamsFOund(data: List<Team>) {
        teams.clear()
        teams.addAll(data)
        listAdapter.notifyDataSetChanged()

        if (data.isEmpty()) textview_empty_list.visible()
        else textview_empty_list.gone()
    }


}

