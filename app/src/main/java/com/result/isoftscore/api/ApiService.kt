package com.result.isoftscore.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import com.result.isoftscore.BuildConfig
import com.result.isoftscore.helper.OkhttpProvider
import com.result.isoftscore.model.*
import kotlinx.coroutines.experimental.Deferred
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("eventsnextleague.php")
    fun getNextMatchList(@Query("id") idLeague: String): Deferred<Match>

    @GET("eventspastleague.php")
    fun getPrevMatchList(@Query("id") idLeague: String): Deferred<Match>

    @GET("searchevents.php")
    fun searchMatch(@Query("e") searchText: String): Deferred<MatchSearch>

    @GET("lookupteam.php")
    fun getTeamDetail(@Query("id") idTeam: String): Deferred<TeamResponse>

    @GET("lookup_all_teams.php")
    fun getTeamList(@Query("id") idLeague: String): Deferred<TeamResponse>

    @GET("lookup_all_players.php")
    fun getPlayerList(@Query("id") idTeam: String): Deferred<PlayerResponse>

    @GET("searchteams.php")
    fun searchTeam(@Query("t") searchText: String): Deferred<TeamSearch>

    companion object {
        fun create(): ApiService {
            val retrofit = Retrofit.Builder()
                    .baseUrl(BuildConfig.TSDB_BASE_URL)
                    .client(OkhttpProvider.getInstance())
                    .addCallAdapterFactory(CoroutineCallAdapterFactory())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            return retrofit.create(ApiService::class.java)
        }
    }

}