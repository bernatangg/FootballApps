package com.result.isoftscore.view

import com.result.isoftscore.model.Team

interface TeamSearchView {
    fun showLoading()
    fun hideLoading()
    fun showTeamsFOund(data: List<Team>)
}

