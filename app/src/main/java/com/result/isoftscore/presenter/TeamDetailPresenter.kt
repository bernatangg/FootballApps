package com.result.isoftscore.presenter

import android.util.Log
import com.result.isoftscore.api.PlayerRepoProvider
import com.result.isoftscore.view.TeamDetailView
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import java.lang.RuntimeException

class TeamDetailPresenter(private val view: TeamDetailView) {
    private val repo = PlayerRepoProvider.providePlayerRepo()
    private var playerListJob = Job()

    fun getPlayerList(idTeam: String?) {
        idTeam?.let {
            playerListJob = launch(UI) {
                try {
                    view.showLoading()
                    val data = repo.getPlayerList(it)
                    view.showPlayerList(data)
                    view.hideLoading()
                } catch (e: RuntimeException) {
                    Log.e(TeamDetailPresenter::class.java.simpleName, e.localizedMessage)
                }
            }
        }
    }

    fun stopPresenting() {
        if (playerListJob.isActive)
            playerListJob.cancel()
    }
}

