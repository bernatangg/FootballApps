package com.result.isoftscore.model

import com.google.gson.annotations.SerializedName

data class TeamSearch(
        @SerializedName("teams")
        val teams: List<Team>?
)