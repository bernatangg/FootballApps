package com.result.isoftscore.api

object PlayerRepoProvider {
    fun providePlayerRepo(): PlayerRepo {
        return PlayerRepo(ApiService.create())
    }
}