package com.result.isoftscore.presenter

import android.content.Context
import android.util.Log
import com.result.isoftscore.api.TeamRepoProvider
import com.result.isoftscore.helper.TeamListType
import com.result.isoftscore.helper.database
import com.result.isoftscore.model.Team
import com.result.isoftscore.view.TeamDetailView
import com.result.isoftscore.view.TeamListView
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.select
import java.lang.RuntimeException

class TeamListPresenter(private val context: Context?,
                        private val view: TeamListView) {
    private val repo = TeamRepoProvider.provideTeamRepo()
    private var teamListJob = Job()

    fun getTeamList(teamListType: TeamListType, leagueId: String?) {
        view.showLoading()
        if (teamListType == TeamListType.LIST_TEAM && leagueId != null) {
            getTeamListFromApi(leagueId)
        } else {
            getFavoritesTeamList()
        }

    }

    private fun getTeamListFromApi(leagueId: String) {
        teamListJob = launch(UI) {
           try {
               val data = repo.getTeamList(leagueId)
               view.showTeamList(data)
               view.hideLoading()
           }  catch (e: RuntimeException) {
               Log.e(TeamListPresenter::class.java.simpleName, e.localizedMessage)
           }
        }
    }

    private fun getFavoritesTeamList() {
        context?.database?.use {
            val result = select(Team.TABLE_TEAM)
            val teams = result.parseList(classParser<Team>())
            view.showTeamList(teams)
            view.hideLoading()
        }
    }

    fun removeAllFavoritesTeam() {
        context?.database?.use {
            delete(Team.TABLE_TEAM)
            val teams: List<Team> = listOf()
            view.showTeamList(teams)
        }
    }

    fun stopPresenting() {
        if (teamListJob.isActive) {
            teamListJob.cancel()
        }
    }
}

