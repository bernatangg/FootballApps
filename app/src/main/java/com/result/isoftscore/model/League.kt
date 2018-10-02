package com.result.isoftscore.model

data class League ( val leagueId: String, val leagueName: String) {
    override fun toString(): String {
        return leagueName
    }
}

