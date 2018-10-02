package com.result.isoftscore.view

import com.result.isoftscore.model.Player

interface TeamDetailView {
    fun showLoading()
    fun hideLoading()
    fun showPlayerList(data: List<Player>)
}