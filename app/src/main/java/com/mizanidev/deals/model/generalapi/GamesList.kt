package com.mizanidev.deals.model.generalapi

import com.google.gson.annotations.SerializedName

data class GamesList(
    @SerializedName("platform")
    val platform: String,
    @SerializedName("slug")
    val slug: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("imageUrl")
    val imageUrl: String,
    @SerializedName("releaseDateDisplay")
    val releaseDateDisplay: String,
    @SerializedName("releaseDateOrder")
    val releaseDateOrder: String,
    @SerializedName("price_info")
    val priceInfo: PriceInfo?

)