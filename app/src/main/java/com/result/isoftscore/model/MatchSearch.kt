package com.result.isoftscore.model

import com.google.gson.annotations.SerializedName

data class MatchSearch (
        @SerializedName("event")
        val events: List<Event>?
)

