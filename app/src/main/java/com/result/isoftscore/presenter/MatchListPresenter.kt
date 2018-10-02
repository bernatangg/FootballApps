package com.result.isoftscore.presenter

import android.content.Context
import com.result.isoftscore.api.MatchRepoProvider
import com.result.isoftscore.helper.MatchListType
import com.result.isoftscore.helper.database
import com.result.isoftscore.model.Event
import com.result.isoftscore.view.MatchListView
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.select

class MatchListPresenter(private val context: Context?, private val view: MatchListView) {
    private val repo = MatchRepoProvider.provideMatchRepo()
    private var matchListJob = Job()

    fun getMatchList(matchListType: MatchListType, leagueId: String?) {
        view.showLoading()
        if (matchListType != MatchListType.FAVORITES_MATCH && leagueId != null) {
            getMatchListFromApi(matchListType, leagueId)
        } else {
            getFavoriteMatchList()
        }
    }

    private fun getMatchListFromApi(matchListType: MatchListType, leagueId: String) {
        matchListJob = async(UI) {
            val data = repo.getMatchList(matchListType, leagueId)
            view.showMatchList(data.await().events)
            view.hideLoading()
        }
    }

    private fun getFavoriteMatchList() {
        context?.database?.use {
            val result = select(Event.TABLE_EVENT)
            val events = result.parseList(classParser<Event>())

            view.hideLoading()
            view.showMatchList(events)
        }
    }

    fun removeAllFavoriteMatch() {
        context?.database?.use {
            delete(Event.TABLE_EVENT)

            val events: List<Event> = listOf()
            view.showMatchList(events)
        }
    }

    fun stopPresenting() {
        if (matchListJob.isActive) {
            matchListJob.cancel()
        }
    }
}