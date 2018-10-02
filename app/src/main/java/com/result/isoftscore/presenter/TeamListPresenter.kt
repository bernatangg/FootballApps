package com.result.isoftscore.presenter

import android.content.Context
import com.result.isoftscore.api.TeamRepoProvider
import com.result.isoftscore.helper.TeamListType
import com.result.isoftscore.helper.database
import com.result.isoftscore.model.Team
import com.result.isoftscore.view.TeamDetailView
import com.result.isoftscore.view.TeamListView
import kotlinx.coroutines.experimental.Job
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.select

class TeamListPresenter(private val context: Context?,
                        private val view: TeamListView) {
    private val repo = TeamRepoProvider.provideTeamRepo()
    private var teamListJob = Job()

    fun getTeamList(teamListType: TeamListType, leagueId: String?) {
        view.showLoading()
        if (teamListType == TeamListType.LIST_TEAM && leagueId != null) {

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

