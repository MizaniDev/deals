package com.mizanidev.deals.model.game

import com.google.gson.annotations.SerializedName

data class SingleGameRequestPublishers(
    @SerializedName("name")
    val name: String
)