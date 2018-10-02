package com.result.isoftscore.api

object TeamRepoProvider {
    fun provideTeamRepo(): TeamRepo {
        return TeamRepo(ApiService.create())
    }
}

