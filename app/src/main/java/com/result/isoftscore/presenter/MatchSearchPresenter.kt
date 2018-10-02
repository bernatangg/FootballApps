package com.result.isoftscore.presenter

import android.util.Log
import com.result.isoftscore.api.MatchRepo
import com.result.isoftscore.api.MatchRepoProvider
import com.result.isoftscore.view.MatchSearchView
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import java.lang.RuntimeException

class MatchSearchPresenter(private val view: MatchSearchView) {
    private val repo: MatchRepo = MatchRepoProvider.provideMatchRepo()
    private var searchMatchJob = Job()

    fun searchMatch(searchText: String) {
        if (searchText.length > 2) {
            checkActiveSearchJob()
            searchMatchJob = launch(UI) {
                try {
                    view.showLoading()
                    val data = repo.searchMatch(searchText)
                    view.showMatchesFound(data)
                    view.hideLoading()
                } catch (e: RuntimeException) {
                    Log.e(MatchSearchPresenter::class.java.simpleName, e.localizedMessage)
                }

            }

        }
    }

    fun stopPresenting() {
        checkActiveSearchJob()
    }

    private fun checkActiveSearchJob() {
        if (searchMatchJob.isActive) {
            searchMatchJob.cancel()
        }
    }
}