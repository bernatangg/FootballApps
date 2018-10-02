package com.result.isoftscore.presenter

import android.util.Log
import com.result.isoftscore.api.TeamRepoProvider
import com.result.isoftscore.view.TeamSearchView
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import java.lang.RuntimeException

class TeamSearchPresenter(private val view: TeamSearchView) {

    private val repo = TeamRepoProvider.provideTeamRepo()
    private var searchTeamJob = Job()

    fun searchTeam(searchText: String) {
        if (searchText.length > 2) {
            checkActiveSearch()
            searchTeamJob = launch(UI) {
                try {
                    view.showLoading()
                    val data = repo.searchTeam(searchText)
                    view.showTeamsFOund(data)
                    view.hideLoading()
                } catch (e: RuntimeException) {
                    Log.e(TeamSearchPresenter::class.java.simpleName, e.localizedMessage)
                }
            }
        }
    }

    private fun checkActiveSearch() {
        if (searchTeamJob.isActive)
            searchTeamJob.cancel()
    }

    fun stopPresenting() {
        checkActiveSearch()
    }
}

