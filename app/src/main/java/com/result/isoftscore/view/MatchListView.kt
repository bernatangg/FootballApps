package com.result.isoftscore.view

import com.result.isoftscore.model.Event

interface MatchListView {
    fun showLoading()
    fun hideLoading()
    fun showMatchList(data: List<Event>)
}

