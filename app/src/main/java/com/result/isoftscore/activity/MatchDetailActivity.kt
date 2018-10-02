package com.result.isoftscore.activity

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import com.result.isoftscore.model.Event
import com.result.isoftscore.presenter.MatchDetailPresenter
import com.result.isoftscore.view.MatchDetailView
import org.jetbrains.anko.startActivity

class MatchDetailActivity: AppCompatActivity(), MatchDetailView {
    private lateinit var event: Event
    private lateinit var presenter: MatchDetailPresenter
    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false

    companion object {
        const val EXTRA_EVENT_DATA = "event-data"
        fun start(context: Context, event: Event) {
            context.startActivity<MatchDetailActivity>(EXTRA_EVENT_DATA to event)
        }
    }

    override fun showHomeImage(urlString: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showAwayImage(urlString: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}

