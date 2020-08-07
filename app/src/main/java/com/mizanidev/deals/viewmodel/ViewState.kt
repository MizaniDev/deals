package com.mizanidev.deals.viewmodel

import com.mizanidev.deals.model.others.Currency

sealed class ViewState {
    // Settings
    object SendToTwitter: ViewState()
    object SendToInstagram: ViewState()
    object RequestError: ViewState()

    data class ShowCurrencies(val listCurrencies: List<Currency>) : ViewState()

}