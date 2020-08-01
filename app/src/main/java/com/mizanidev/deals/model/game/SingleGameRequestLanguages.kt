package com.mizanidev.deals.model.game

import com.google.gson.annotations.SerializedName

data class SingleGameRequestLanguages(
    @SerializedName("region")
    val region: String,
    @SerializedName("languages")
    val languages: List<SingleGameRequestLanguagesByRegion>
)