package com.mizanidev.deals.api

import com.mizanidev.deals.model.GamesRequest
import com.mizanidev.deals.model.SingleGameRequest
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
    @GET("games{gameToShow}?")
    fun showGame(@Path("gameToShow") title: String, @QueryMap query: Map<String, String>): Call<SingleGameRequest>
}