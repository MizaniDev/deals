package com.mizanidev.deals.model.game

import com.google.gson.annotations.SerializedName

data class SingleGameRequest(
    @SerializedName("data")
    val data: SingleGameRequestInfo
)