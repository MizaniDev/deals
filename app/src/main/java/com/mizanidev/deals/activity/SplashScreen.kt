package com.mizanidev.deals.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.mizanidev.deals.R

class SplashScreen : AppCompatActivity() {
    private val splashTimer: Long = 5000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setTimer()
    }

    private fun setTimer(){
        val handler = Handler()
        handler.postDelayed({
            callMainScreen()
        }, splashTimer)
    }

    private fun callMainScreen(){
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}
