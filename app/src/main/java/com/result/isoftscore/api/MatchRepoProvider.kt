package com.result.isoftscore.api

object MatchRepoProvider {

    fun provideMatchRepo(): MatchRepo {
        return MatchRepo(ApiService.create())
    }
}