package com.result.isoftscore.model

import com.google.gson.annotations.SerializedName

data class PlayerResponse(
        @SerializedName("player")
        val player: List<Player>
)