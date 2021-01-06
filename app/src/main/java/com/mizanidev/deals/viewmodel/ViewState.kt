package com.mizanidev.deals.viewmodel

import android.content.Intent
import com.mizanidev.deals.model.game.SingleGameRequestInfo
import com.mizanidev.deals.model.generalapi.GamesList
import com.mizanidev.deals.model.generalapi.GamesRequest
import com.mizanidev.deals.model.generic.KnowsBugs
import com.mizanidev.deals.model.others.Currency

sealed class ViewState {
    //Session
    object UserLogged: ViewState()

    // Settings
    object RequestError: ViewState()
    object Idle: ViewState()
    object Loading: ViewState()
    object Loaded: ViewState()

    object LoadingRecyclerView: ViewState()
    object LoadedRecyclerView: ViewState()

    data class ShowCurrencies(val listCurrencies: List<Currency>) : ViewState()
    data class ShowRecentReleases(var items: GamesRequest?) : ViewState()
    data class ShowOnSale(var items: GamesRequest?) : ViewState()
    data class ShowSoonGames(var items: GamesRequest?) : ViewState()
    data class ShowGame(var game: SingleGameRequestInfo) : ViewState()
    data class ShowMoreGames(var items: GamesRequest?): ViewState()

    object ShowSuggestionAlert: ViewState()
    object SuggestionSent: ViewState()
    object SuggestionError: ViewState()
    object StartPurchase: ViewState()

    data class RateApp(var intent: Intent): ViewState()

    //Search
    object SearchLoading: ViewState()
    object SearchLoaded: ViewState()
    data class RefreshSearch(var items: GamesRequest?) : ViewState()

    //Knows Bugs
    data class ShowBugs(var items: ArrayList<KnowsBugs>) : ViewState()
    object NoBugs: ViewState()
}