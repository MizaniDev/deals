package com.mizanidev.deals.model.generalapi

import com.google.gson.annotations.SerializedName

data class GamesRequest(
    @SerializedName("links")
    val links: Links?,
    @SerializedName("data")
    val gameLists: List<GamesList>
)