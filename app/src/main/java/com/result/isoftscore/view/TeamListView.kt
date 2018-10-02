package com.result.isoftscore.view

import com.result.isoftscore.model.Team

interface TeamListView {
    fun showLoading()
    fun hideLoading()
    fun showTeamList(data: List<Team>)
}