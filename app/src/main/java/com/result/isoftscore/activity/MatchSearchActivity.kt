package com.result.isoftscore.activity

import android.content.Context
import android.support.v7.app.AppCompatActivity
import com.result.isoftscore.adapter.MatchListAdapter
import com.result.isoftscore.model.Event
import com.result.isoftscore.presenter.MatchSearchPresenter
import com.result.isoftscore.view.MatchSearchView
import org.jetbrains.anko.startActivity

class MatchSearchActivity : AppCompatActivity(), MatchSearchView {

    private lateinit var searchView: MatchSearchView
    private val events: MutableList<Event> = mutableListOf()
    private lateinit var presenter: MatchSearchPresenter
    private lateinit var listAdapter: MatchListAdapter

    companion object {
        fun start(context: Context) {
            context.startActivity<MatchSearchActivity>()
        }

    }

    override fun showLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showMatchesFound(data: List<Event>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}

