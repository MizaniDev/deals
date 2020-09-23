package com.mizanidev.deals

import android.app.Application
import com.google.android.gms.ads.MobileAds
import com.mizanidev.deals.modules.mainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application(){

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyApplication)
            modules(mainModule)
        }
    }
}