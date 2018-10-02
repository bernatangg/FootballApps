package com.result.isoftscore.model

import com.google.gson.annotations.SerializedName

data class TeamResponse(
        @SerializedName("teams")
        val teams: List<Team>
)

