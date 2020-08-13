package com.mizanidev.deals.model.generalapi

import com.google.gson.annotations.SerializedName

data class PriceInfo(
    @SerializedName("best_price")
    val bestPrice: Boolean,
    @SerializedName("url_eshop")
    val urlEshop: String,
    @SerializedName("currentPrice")
    val currentPrice: String,
    @SerializedName("rawCurrentPrice")
    val rawCurrentPrice: Double,
    @SerializedName("hasDiscount")
    val hasDiscount: Boolean = false,
    @SerializedName("status")
    val status: String,
    @SerializedName("goldPoints")
    val goldPoints: Int,
    @SerializedName("digital_release")
    val digitalRelease: Boolean,
    @SerializedName("physical_release")
    val physicalRelease: Boolean,
    @SerializedName("country")
    val country: Country?,
    @SerializedName("regularPrice")
    val regularPrice: RegularPrice?,
    @SerializedName("discountPrice")
    val discountPrice: DiscountPrice?
)