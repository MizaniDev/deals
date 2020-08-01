package com.mizanidev.deals.model

import com.google.gson.annotations.SerializedName

data class ListaGames(
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
    val priceInfo: PriceInfo

)