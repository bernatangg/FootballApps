package com.result.isoftscore.api

import com.result.isoftscore.helper.LeagueData
import com.result.isoftscore.helper.MatchListType
import com.result.isoftscore.model.Event
import com.result.isoftscore.model.Match
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.withContext

class MatchRepo(private val apiService: ApiService) {

    fun getMatchList(matchListType: MatchListType, leagueId: String): Deferred<Match> {
        return when (matchListType) {
            MatchListType.NEXT_MATCH -> apiService.getNextMatchList(leagueId)
            else -> apiService.getPrevMatchList(leagueId)
        }
    }

    suspend fun searchMatch(searchText: String): List<Event> = withContext(CommonPool) {
        val events: MutableList<Event> = mutableListOf()
        val request = apiService.searchMatch(searchText)
        val response = request.await().events

        if (response != null) {
            for (event in response) {

                val leagues = LeagueData.provideLeagueData()
                for (league in leagues) {
                    if (event.idLeague == league.leagueId) {
                        events.add(event)
                        break
                    }
                }
            }
        }

        events
    }
}

