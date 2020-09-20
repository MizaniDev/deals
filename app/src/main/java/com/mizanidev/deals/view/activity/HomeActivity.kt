package com.mizanidev.deals.view.activity

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.mizanidev.deals.R
import com.mizanidev.deals.util.SharedPreferenceConstants
import com.mizanidev.deals.util.SharedPreferenceUtil
import com.mizanidev.deals.view.ScreenFlow
import com.mizanidev.deals.view.fragments.BaseFragment
import com.mizanidev.deals.view.fragments.DealsInterface
import com.mizanidev.deals.view.fragments.onsale.OnSaleFragment
import com.mizanidev.deals.view.fragments.releases.ReleasesFragment
import com.mizanidev.deals.view.fragments.settings.SettingsFragment
import com.mizanidev.deals.view.fragments.soon.SoonFragment
import java.util.*

open class HomeActivity : AppCompatActivity(), ScreenFlow, DealsInterface {

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_recent_releases -> {
                callFragment(ReleasesFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_deals -> {
                callFragment(OnSaleFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_soon -> {
                callFragment(SoonFragment())
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
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        navView.selectedItemId = R.id.navigation_recent_releases

        generateUUID()
    }

    override fun onAttachFragment(fragment: Fragment) {
        if(fragment is BaseFragment) {
            fragment.listener = this
        }
    }

    override fun callFragment(fragment: Fragment) {
        val fragmentOptions = supportFragmentManager.beginTransaction()
        fragmentOptions.replace(R.id.content, fragment)
        fragmentOptions.commit()
    }

    override fun sharedPreference(): SharedPreferenceUtil {
        return SharedPreferenceUtil(this)
    }

    private fun generateUUID() {
        val uuid = UUID.randomUUID()
        if(!sharedPreference().keyExists(SharedPreferenceConstants.UUID)) {
            sharedPreference().saveConfig(SharedPreferenceConstants.UUID, uuid.toString())
        }
    }
}
