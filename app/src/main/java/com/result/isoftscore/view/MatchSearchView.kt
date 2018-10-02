package com.result.isoftscore.view

import com.result.isoftscore.model.Event

interface MatchSearchView {
    fun showLoading()
    fun hideLoading()
    fun showMatchesFound(data: List<Event>)
}

