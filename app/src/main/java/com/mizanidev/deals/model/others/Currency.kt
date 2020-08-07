package com.mizanidev.deals.model.others

import com.google.gson.annotations.SerializedName

data class Currency(
    @SerializedName("code")
    val code: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("usd")
    val usd: String
)

data class CurrencyData(
    @SerializedName("data")
    val currencies: List<Currency>
)