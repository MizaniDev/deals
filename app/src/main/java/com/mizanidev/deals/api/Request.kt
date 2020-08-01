package com.mizanidev.deals.api

import com.mizanidev.deals.util.Url
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


}