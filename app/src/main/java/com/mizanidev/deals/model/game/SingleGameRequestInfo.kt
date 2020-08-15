package com.mizanidev.deals.model.game

import com.google.gson.annotations.SerializedName

data class SingleGameRequestInfo(
    @SerializedName("platform")
    val platform: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("imageUrl")
    val imageUrl: String,
    @SerializedName("releaseDate")
    val releaseDate: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("numberOfPlayers")
    val numberOfPlayers: String,
    @SerializedName("hasDemo")
    val hasDemo: Boolean,
    @SerializedName("size")
    val size: Long,
    @SerializedName("youtubeId")
    val youtubeId: String?,
    @SerializedName("publishers")
    val publishers: List<SingleGameRequestPublishers>?,
    @SerializedName("languages")
    val languages: List<SingleGameRequestLanguages>?,
    @SerializedName("categories")
    val categories: List<String>,
    @SerializedName("price_info")
    val priceInfo: SingleGameRequestPrice?
)