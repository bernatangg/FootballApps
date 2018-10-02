package com.result.isoftscore.api

import android.text.AutoText
import com.result.isoftscore.helper.LeagueData
import com.result.isoftscore.model.League
import com.result.isoftscore.model.Team
import com.result.isoftscore.model.TeamResponse
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.withContext

class TeamRepo(private val apiService: ApiService) {

    fun getTeamDetail(idTeam: String): Deferred<TeamResponse> {
        return apiService.getTeamDetail(idTeam)
    }

    suspend fun getTeamList(idLeague: String): List<Team> = withContext(CommonPool) {
        val request = apiService.getTeamList(idLeague)
        val response = request.await().teams

        response
    }

    suspend fun searchTeam(searchText: String): List<Team> = withContext(CommonPool) {
        val teams: MutableList<Team> = mutableListOf()
        val request = apiService.searchTeam(searchText)
        val response = request.await().teams

        response?.let {
            for (team in it) {
                val leagues = LeagueData.provideLeagueData()
                for (league in leagues) {
                    if (team.idLeague == league.leagueId) {
                        teams.add(team)
                        break
                    }
                }
            }
        }
        teams
    }
}