package com.mizanidev.deals.viewmodel

import com.mizanidev.deals.model.game.SingleGameRequestInfo
import com.mizanidev.deals.model.generalapi.GamesList
import com.mizanidev.deals.model.others.Currency

sealed class ViewState {
    // Settings
    object SendToTwitter: ViewState()
    object SendToInstagram: ViewState()
    object RequestError: ViewState()
    object Idle: ViewState()
    object Loading: ViewState()
    object Loaded: ViewState()

    data class ShowCurrencies(val listCurrencies: List<Currency>) : ViewState()
    data class ShowRecentReleases(var items: List<GamesList>) : ViewState()
    data class ShowOnSale(var items: List<GamesList>) : ViewState()
    data class ShowGame(var game: SingleGameRequestInfo) : ViewState()

}