package com.mizanidev.deals.model

import com.google.gson.annotations.SerializedName

data class GamesRequest(
    @SerializedName("links")
    val links: Links,
    @SerializedName("data")
    val listaGames: List<ListaGames>
)