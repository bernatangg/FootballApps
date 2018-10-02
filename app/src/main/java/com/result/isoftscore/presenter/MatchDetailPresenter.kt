package com.result.isoftscore.presenter

import com.result.isoftscore.api.TeamRepoProvider
import com.result.isoftscore.model.Team
import com.result.isoftscore.view.MatchDetailView
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async

class MatchDetailPresenter (private val view: MatchDetailView) {
    private val repo = TeamRepoProvider.provideTeamRepo()
    private val parentJob = Job()

    fun getHomeImage(idTeam: String) {
        val homeImageJob = async(UI, parent = parentJob) {
            val data = repo.getTeamDetail(idTeam)
            val teamData: Team = data.await().teams[0]
            view.showHomeImage(teamData.teamBadge)
        }
    }

    fun getAwayImage(idTeam: String) {
        val awayImageJob = async(UI, parent = parentJob) {
            val data = repo.getTeamDetail(idTeam)
            val teamData: Team = data.await().teams[0]
            view.showAwayImage(teamData.teamBadge)
        }
    }

    fun stopPresenting() {
        if (parentJob.isActive) {
            parentJob.cancel()
        }
    }
}