package com.result.isoftscore.helper

import com.result.isoftscore.model.League

object LeagueData {

    fun provideLeagueData(): List<League> {
        val leagueState: MutableList<League> = mutableListOf()

        leagueState.add(League("4328", "English Premier League"))
        leagueState.add(League("4329", "English League Championship"))
        leagueState.add(League("4345", "Spanish La Liga"))
        leagueState.add(League("4331", "German Bundesliga"))
        leagueState.add(League("4332", "Italian Serie A"))
        leagueState.add(League("4334", "French Ligue 1"))
        leagueState.add(League("4337", "Dutch Eredivisie"))

        return leagueState
    }
}

