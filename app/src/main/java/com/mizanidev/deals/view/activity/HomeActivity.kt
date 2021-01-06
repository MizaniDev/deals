package com.mizanidev.deals.view.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.anjlab.android.iab.v3.BillingProcessor
import com.anjlab.android.iab.v3.TransactionDetails
import com.mizanidev.deals.R
import com.mizanidev.deals.util.CToast
import com.mizanidev.deals.util.SharedPreferenceConstants
import com.mizanidev.deals.util.SharedPreferenceUtil
import com.mizanidev.deals.util.Util
import com.mizanidev.deals.view.ScreenFlow
import com.mizanidev.deals.view.fragments.BaseFragment
import com.mizanidev.deals.view.fragments.DealsInterface
import com.mizanidev.deals.view.fragments.onsale.OnSaleFragment
import com.mizanidev.deals.view.fragments.releases.ReleasesFragment
import com.mizanidev.deals.view.fragments.settings.SettingsFragment
import com.mizanidev.deals.view.fragments.soon.SoonFragment
import java.util.*
const val LICENCE_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAkZWjtvnmvLS/SwrQQeue+exAEcXfwy15B3Pkpu0pkkxh2RdoYW5L5+bvfGwAW/nsHxgrCDxwxX8paAy/xCFHuWYJD+ToZs08FUbyuCd/QVN//0K4HFb6sYymcmgd6W2PZ7A4J/Y4iciMSMtl7VHBJzNs52FlP2Z4uyee5WH0UwQYErTuzc40c7dBmg5q/D5qutxkLv7+vS/WfjZSqj1YCr5hDfNzAR087m9MOptI/dVP/My1O0kPlJeWoHt+se5c38d5sE/gC8NdSxx2iBvxYyE3ybkPicoitJwTqpYY382IQdD0sUq/Ggfs6XBveFu8C5qJo2rIUbuZUFBpDAK0OQIDAQAB"

open class HomeActivity : AppCompatActivity(),
    ScreenFlow, DealsInterface, BillingProcessor.IBillingHandler {

    private lateinit var billingProcessor: BillingProcessor

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

        billingProcessor = BillingProcessor(this,
            LICENCE_KEY, this)
    }

    override fun onAttachFragment(fragment: Fragment) {
        if(fragment is BaseFragment) {
            fragment.listener = this
            fragment.billingProcessor = billingProcessor
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

    override fun onBillingInitialized() {
        Log.i("BILLING", "Initialized")
    }

    override fun onPurchaseHistoryRestored() {
        Log.i("BILLING", "Purchased Restored")
        if(billingProcessor.isPurchased("deals_removeads_2020")) {
            Log.i("BILLING", "Product Purchased")
            Util.APP_PURCHASED = true
        } else {
            Log.i("BILLING", "Product Not Purchased")
            Util.APP_PURCHASED = false
        }
    }

    override fun onProductPurchased(productId: String, details: TransactionDetails?) {
        Log.i("BILLING", "Product Purchased")
        Util.APP_PURCHASED = true
    }

    override fun onBillingError(errorCode: Int, error: Throwable?) {
        val cToast = CToast(this)
        cToast.showError(getString(R.string.error_purchase))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(!billingProcessor.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onDestroy() {
        billingProcessor.release()
        super.onDestroy()
    }
}
