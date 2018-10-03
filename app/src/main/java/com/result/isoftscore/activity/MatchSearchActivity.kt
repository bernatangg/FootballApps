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
import com.result.isoftscore.adapter.MatchListAdapter
import com.result.isoftscore.helper.BottomDecoration
import com.result.isoftscore.model.Event
import com.result.isoftscore.presenter.MatchSearchPresenter
import com.result.isoftscore.util.gone
import com.result.isoftscore.util.nullToEmpty
import com.result.isoftscore.util.toPx
import com.result.isoftscore.util.visible
import com.result.isoftscore.view.MatchSearchView
import kotlinx.android.synthetic.main.activity_search.*
import org.jetbrains.anko.ctx
import org.jetbrains.anko.startActivity

class MatchSearchActivity : AppCompatActivity(), MatchSearchView {
    private lateinit var searchView: SearchView

    private val events: MutableList<Event> = mutableListOf()
    private lateinit var presenter: MatchSearchPresenter
    private lateinit var listAdapter: MatchListAdapter

    companion object {
        fun start(context: Context) {
            context.startActivity<MatchSearchActivity>()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        setupToolbar()
        setupRecyclerView()

        presenter = MatchSearchPresenter(this)
    }

    private fun setupToolbar() {
        supportActionBar?.apply {
            setTitle(R.string.search_match)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun setupRecyclerView() {
        listAdapter = MatchListAdapter(events) {
            MatchDetailActivity.start(this, it)
        }

        recyclerview_search.apply {
            layoutManager = LinearLayoutManager(ctx)
            addItemDecoration(BottomDecoration(16.toPx()))
            adapter = listAdapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search, menu)
        val menuSearch = menu?.findItem(R.id.action_search)
        searchView = menuSearch?.actionView as SearchView
        setupSearchView()

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            } else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupSearchView() {
        searchView.queryHint = getString(R.string.search)
        searchView.isIconified = false
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean { return false }

            override fun onQueryTextChange(newText: String?): Boolean {
                presenter.searchMatch(newText.nullToEmpty())
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

    override fun showMatchesFound(data: List<Event>) {
        events.clear()
        events.addAll(data)
        listAdapter.notifyDataSetChanged()
        if (data.isEmpty())
            textview_empty_list.visible()
        else
            textview_empty_list.gone()
    }


}

