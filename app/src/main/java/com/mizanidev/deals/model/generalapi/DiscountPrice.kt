package com.mizanidev.deals.model.generalapi

import com.google.gson.annotations.SerializedName

data class DiscountPrice(
    @SerializedName("rawOriginalDiscountPrice")
    val rawOriginalDiscountPrice: Double,
    @SerializedName("originalDiscountPrice")
    val originalDiscountPrice: String,
    @SerializedName("rawDiscountPrice")
    val rawDiscountPrice: Double,
    @SerializedName("discountPrice")
    val discountPrice: String,
    @SerializedName("discountBeginsAt")
    val discountBeginsAt: String,
    @SerializedName("discountEndsAt")
    val discountEndsAt: String,
    @SerializedName("percentOff")
    val percentOff: Int
)