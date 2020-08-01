package com.mizanidev.deals.model.game

import com.google.gson.annotations.SerializedName

data class SingleGameRequestLanguagesByRegion(
    @SerializedName("code")
    val code: String,
    @SerializedName("name")
    val name: String
)