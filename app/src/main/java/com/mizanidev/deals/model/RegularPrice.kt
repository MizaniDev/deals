package com.mizanidev.deals.model

import com.google.gson.annotations.SerializedName

data class RegularPrice(
    @SerializedName("rawOriginalRegularPrice")
    val rawOriginalRegularPrice: Double,
    @SerializedName("originalRegularPrice")
    val originalRegularPrice: String,
    @SerializedName("rawRegularPrice")
    val rawRegularPrice: Double,
    @SerializedName("regularPrice")
    val regularPrice: String
)