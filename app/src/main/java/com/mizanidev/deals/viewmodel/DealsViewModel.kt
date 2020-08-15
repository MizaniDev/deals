package com.mizanidev.deals.viewmodel

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.mizanidev.deals.api.Request
import com.mizanidev.deals.model.game.SingleGameRequest
import com.mizanidev.deals.model.generalapi.GamesRequest
import com.mizanidev.deals.model.others.CurrencyData
import com.mizanidev.deals.model.utils.Settings
import com.mizanidev.deals.model.utils.SettingsIds
import com.mizanidev.deals.util.Url
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class DealsViewModel(private val context: Context) : BaseViewModel(){
    val viewState = MutableLiveData<ViewState>()
    private val scope = CoroutineScope(Job())

    fun configureSettings(setting: Settings){
        when (setting.idSettings) {
            SettingsIds.ID_REGION -> selectRegion()
            SettingsIds.ID_CURRENCY -> selectCurrency()
            SettingsIds.ID_TRANSLATE -> helpWithTranslation()
            SettingsIds.ID_SUGGESTIONS -> sendSuggestions()
            SettingsIds.ID_BUGS -> showBugs()
            SettingsIds.ID_TWITTER -> navigateTwitter()
            SettingsIds.ID_INSTAGRAM -> navigateInstagram()
            SettingsIds.ID_RATE -> rateApp()
        }

    }

    fun requestRecentReleases(currency: String, locale: String, platform: String){
//        viewState.value = ViewState.Loading
//        scope.launch{
//            try {
//                val requestInteractor = Request()
//                val request = requestInteractor.requestRecentReleases(currency, locale, platform)
//
//                request.enqueue(object: Callback<GamesRequest>{
//                    override fun onFailure(call: Call<GamesRequest>, t: Throwable) {
//                        viewState.value = ViewState.RequestError
//                        viewState.value = ViewState.Loaded
//                        viewState.value = ViewState.Idle
//                    }
//
//                    override fun onResponse(call: Call<GamesRequest>, response: Response<GamesRequest>) {
//                        val gameRequest = response.body()
//                        viewState.value = ViewState.Loaded
//                        viewState.value = ViewState.ShowRecentReleases(gameRequest!!.gameLists)
//                        viewState.value = ViewState.Idle
//                    }
//
//                })
//            }catch (exception: Exception){
//                Log.e("REQUESTERROR", exception.message)
//                viewState.value = ViewState.RequestError
//                viewState.value = ViewState.Loaded
//                viewState.value = ViewState.Idle
//            }
//        }
    }
//
    fun requestSoonGames(currency: String, locale: String, platform: String){
//        viewState.value = ViewState.Loading
//        scope.launch{
//            try {
//                val requestInteractor = Request()
//                val request = requestInteractor.requestSoonGames(currency, locale, platform)
//
//                request.enqueue(object: Callback<GamesRequest>{
//                    override fun onFailure(call: Call<GamesRequest>, t: Throwable) {
//                        viewState.value = ViewState.RequestError
//                        viewState.value = ViewState.Loaded
//                        viewState.value = ViewState.Idle
//                    }
//
//                    override fun onResponse(call: Call<GamesRequest>, response: Response<GamesRequest>) {
//                        val gameRequest = response.body()
//                        viewState.value = ViewState.Loaded
//                        viewState.value = ViewState.ShowOnSale(gameRequest!!.gameLists)
//                        viewState.value = ViewState.Idle
//                    }
//
//                })
//            }catch (exception: Exception){
//                Log.e("REQUESTERROR", exception.message)
//                viewState.value = ViewState.RequestError
//                viewState.value = ViewState.Loaded
//                viewState.value = ViewState.Idle
//            }
//        }
    }

    fun requestGame(title: String){
        viewState.value = ViewState.Loading
        scope.launch{
            try {
                val requestInteractor = Request()
                val request = requestInteractor.requestGame(title, context)
                request.enqueue(object: Callback<SingleGameRequest>{
                    override fun onFailure(call: Call<SingleGameRequest>, t: Throwable) {
                        viewState.value = ViewState.RequestError
                        viewState.value = ViewState.Loaded
                    }

                    override fun onResponse(call: Call<SingleGameRequest>, response: Response<SingleGameRequest>) {
                        val gameRequest = response.body()
                        viewState.value = gameRequest?.data?.let { ViewState.ShowGame(it) }
                        viewState.value = ViewState.Loaded
                    }

                })
            }catch (exception: Exception){
                Log.e("REQUESTERROR", exception.message)
                viewState.value = ViewState.RequestError
                viewState.value = ViewState.Loaded
            }
        }
    }

    private fun selectRegion() {
        //TODO verificar se isso existirá
    }

    private fun selectCurrency() {
        scope.launch {
            try {
                val requestInteractor = Request()
                val request = requestInteractor.requestCurrencies()

                request.enqueue(object: Callback<CurrencyData> {
                    override fun onFailure(call: Call<CurrencyData>, t: Throwable) {
                        viewState.value = ViewState.RequestError
                        viewState.value = ViewState.Idle
                    }

                    override fun onResponse(call: Call<CurrencyData>, response: Response<CurrencyData>) {
                        val currencyRequest = response.body()
                        viewState.value = ViewState.ShowCurrencies(currencyRequest!!.currencies)
                        viewState.value = ViewState.Idle
                    }

                })
            }catch (exception: Exception){
                Log.e("REQUESTERROR", exception.message)
                viewState.value = ViewState.RequestError
                viewState.value = ViewState.Idle
            }
        }
    }

    private fun helpWithTranslation(){

    }

    private fun sendSuggestions(){

    }

    private fun showBugs(){

    }

    private fun navigateTwitter() {
        var twitterIntent: Intent?

        try {
            context.packageManager.getPackageInfo("com.twitter.android", 0)
            twitterIntent = Intent(Intent.ACTION_VIEW, Uri.parse(Url.twitterApp()))
            twitterIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        } catch (noAppException: Exception) {
            twitterIntent = Intent(Intent.ACTION_VIEW, Uri.parse(Url.twitterUrl()))
        }

        context.startActivity(twitterIntent)

    }

    private fun navigateInstagram(){
        var instagramIntent: Intent?

        try {
            context.packageManager.getPackageInfo("com.instagram.android", 0)
            instagramIntent = Intent(Intent.ACTION_VIEW, Uri.parse(Url.instagramApp()))
            instagramIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        } catch (noAppException: Exception) {
            instagramIntent = Intent(Intent.ACTION_VIEW, Uri.parse(Url.instagramUrl()))
        }

        context.startActivity(instagramIntent)
    }

    private fun rateApp(){

    }
}