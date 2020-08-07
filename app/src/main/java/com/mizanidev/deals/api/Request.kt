package com.mizanidev.deals.api

import com.mizanidev.deals.model.others.CurrencyData
import com.mizanidev.deals.util.Url
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class Request{
    companion object{
        private fun retrofitInstance(): Retrofit{
            return Retrofit.Builder()
                .baseUrl(Url.baseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }

    fun requestCurrencies() : Call<CurrencyData> {
        val retrofitClient = retrofitInstance()
        val endpoint = retrofitClient.create(NintendoEndpoint::class.java)

        return endpoint.currencies()
    }

}