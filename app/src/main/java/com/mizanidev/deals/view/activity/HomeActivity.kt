package com.mizanidev.deals.view.activity

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.mizanidev.deals.R
import com.mizanidev.deals.util.SharedPreferenceUtil
import com.mizanidev.deals.view.ScreenFlow
import com.mizanidev.deals.view.fragments.BaseFragment
import com.mizanidev.deals.view.fragments.DealsInterface
import com.mizanidev.deals.view.fragments.settings.SettingsFragment

open class HomeActivity : AppCompatActivity(), ScreenFlow, DealsInterface {

    private lateinit var textMessage: TextView
    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_recent_releases -> {
                textMessage.setText(R.string.recent_releases)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_deals -> {
                textMessage.setText(R.string.deals)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_soon -> {
                textMessage.setText(R.string.soon)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_settings -> {
                callFragment(SettingsFragment())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        textMessage = findViewById(R.id.message)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }

    override fun onAttachFragment(fragment: Fragment) {
        if(fragment is BaseFragment) {
            fragment.listener = this
        }
    }

    override fun callFragment(fragment: Fragment) {
        val fragmentOptions = supportFragmentManager.beginTransaction()
        fragmentOptions.replace(R.id.container, fragment)
        fragmentOptions.commit()
    }

    override fun sharedPreference(): SharedPreferenceUtil {
        return SharedPreferenceUtil(this)
    }
}
