package com.mizanidev.deals.model.game

import com.google.gson.annotations.SerializedName
import com.mizanidev.deals.model.generalapi.Country

data class SingleGameRequestPrice(
    @SerializedName("best_price")
    val bestPrice: Boolean,
    @SerializedName("currentPrice")
    val currentPrice: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("goldPoints")
    val goldPoints: Int = 0,
    @SerializedName("physical_release")
    val physicalRelease: Boolean,
    @SerializedName("country")
    val country: Country?
)