package com.mizanidev.deals.view.fragments

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.mizanidev.deals.api.Request
import com.mizanidev.deals.model.generalapi.GamesRequest
import com.mizanidev.deals.viewmodel.BaseViewModel
import com.mizanidev.deals.viewmodel.ViewState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class BaseFragmentViewModel : BaseViewModel() {
    val viewState = MutableLiveData<ViewState>()
    private val scope = CoroutineScope(Job())

    fun searchGame(nameToSearch: String, context: Context) {
        viewState.value = ViewState.SearchLoading
        scope.launch {
            try {
                val requestInteractor = Request()
                val request = requestInteractor.requestSearchGame(nameToSearch, context)

                request.enqueue(object : Callback<GamesRequest> {
                    override fun onFailure(call: Call<GamesRequest>, t: Throwable) {
                        viewState.value = ViewState.RequestError
                        viewState.value = ViewState.SearchLoaded
                        viewState.value = ViewState.Idle
                    }

                    override fun onResponse(call: Call<GamesRequest>, response: Response<GamesRequest>) {
                        val gameRequest = response.body()
                        viewState.value = ViewState.SearchLoaded
                        viewState.value = ViewState.RefreshSearch(gameRequest)
                        viewState.value = ViewState.Idle
                    }

                })
            }catch (exception: Exception) {
                Log.e("REQUESTERROR", exception.message)
                viewState.value = ViewState.RequestError
                viewState.value = ViewState.SearchLoaded
                viewState.value = ViewState.Idle
            }
        }
    }
}