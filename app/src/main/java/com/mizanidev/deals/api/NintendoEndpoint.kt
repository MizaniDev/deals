package com.mizanidev.deals.api

import com.mizanidev.deals.model.generalapi.GamesRequest
import com.mizanidev.deals.model.game.SingleGameRequest
import com.mizanidev.deals.model.others.CurrencyData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface NintendoEndpoint {

    @GET("games?")
    fun gamesOnSale(@QueryMap query: Map<String, String>, @Query("filter[on_sale]") onSale: Boolean): Call<GamesRequest>
    @GET("games?")
    fun recentReleases(@QueryMap query: Map<String, String>, @Query("filter[recent_release]") recentRelease: Boolean): Call<GamesRequest>
    @GET("games/{gameToShow}?")
    fun showGame(@Path("gameToShow") title: String, @QueryMap query: Map<String, String>): Call<SingleGameRequest>
    @GET("games?")
    fun soonGames(@QueryMap query: Map<String, String>, @Query("filter[coming_soon]") recentRelease: Boolean): Call<GamesRequest>
    @GET("currencies")
    fun currencies() : Call<CurrencyData>
    @GET("games?")
    fun showMoreGames(@QueryMap query: Map<String, String>): Call<GamesRequest>
}