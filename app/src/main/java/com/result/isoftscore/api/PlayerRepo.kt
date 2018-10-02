package com.result.isoftscore.api

import com.result.isoftscore.model.Player
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.withContext

class PlayerRepo(private val apiService: ApiService) {
    suspend fun getPlayerList(idTeam: String): List<Player> = withContext(CommonPool) {
        val request = apiService.getPlayerList(idTeam)
        val response = request.await().player

        response
    }
}

