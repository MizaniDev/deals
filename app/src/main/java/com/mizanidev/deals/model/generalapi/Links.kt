package com.mizanidev.deals.model.generalapi

import com.google.gson.annotations.SerializedName

data class Links(
    @SerializedName("actual")
    val actualLink: String,
    @SerializedName("first")
    val firstLink: String,
    @SerializedName("previous")
    val previousLink: String,
    @SerializedName("next")
    val nextLink: String,
    @SerializedName("last")
    val lastLink: String,
    @SerializedName("self")
    val self: String
)