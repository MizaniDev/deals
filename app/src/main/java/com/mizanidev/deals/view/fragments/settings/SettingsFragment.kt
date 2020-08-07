package com.mizanidev.deals.view.fragments.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mizanidev.deals.BuildConfig
import com.mizanidev.deals.R
import com.mizanidev.deals.model.others.Currency
import com.mizanidev.deals.model.utils.Settings
import com.mizanidev.deals.model.utils.SettingsIds
import com.mizanidev.deals.util.SharedPreferenceConstants
import com.mizanidev.deals.view.fragments.BaseFragment
import com.mizanidev.deals.view.fragments.RecyclerViewSettingsListener
import com.mizanidev.deals.view.fragments.settings.currency.CurrencyAlertList
import com.mizanidev.deals.view.fragments.settings.currency.CurrencyCallback
import com.mizanidev.deals.viewmodel.DealsViewModel
import com.mizanidev.deals.viewmodel.ViewState
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

private const val REGION_VIEW = 1
private const val HELP_US = 2
private const val FOLLOW_US = 3
private const val APP_DETAILS = 4

class SettingsFragment: BaseFragment(), RecyclerViewSettingsListener, CurrencyCallback {
    private lateinit var settingRegion: RecyclerView
    private lateinit var settingHelp: RecyclerView
    private lateinit var settingFollow: RecyclerView
    private lateinit var settingApp: RecyclerView

    private val viewModel: DealsViewModel by sharedViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponents(view)
        setOptions()
        observeViewModel()
    }

    private fun initComponents(view: View){
        settingRegion = view.findViewById(R.id.recycler_settings_region)
        settingHelp = view.findViewById(R.id.recycler_settings_help)
        settingFollow = view.findViewById(R.id.recycler_settings_follow)
        settingApp = view.findViewById(R.id.recycler_settings_details)
    }

    private fun setOptions(){
        settingRegion.adapter = SettingsAdapter(
            requireContext(),
            showSettings(REGION_VIEW),
            this
        )
        settingRegion.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        settingHelp.adapter = SettingsAdapter(
            requireContext(),
            showSettings(HELP_US),
            this
        )
        settingHelp.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        settingFollow.adapter = SettingsAdapter(
            requireContext(),
            showSettings(FOLLOW_US),
            this
        )
        settingFollow.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        settingApp.adapter = SettingsAdapter(
            requireContext(),
            showSettings(APP_DETAILS),
            this
        )
        settingApp.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
    }

    private fun observeViewModel(){
        viewModel.viewState.observe(viewLifecycleOwner, Observer {
            when(it){
                is ViewState.ShowCurrencies -> showCurrencies(it.listCurrencies)
            }
        })
    }

    private fun showSettings(viewType: Int) : List<Settings>{
        when(viewType){
            REGION_VIEW -> {
                return listOf(
                    Settings(SettingsIds.ID_REGION, R.drawable.region, getString(R.string.region)),

                    Settings(
                        SettingsIds.ID_CURRENCY,
                        R.drawable.currency,
                        getString(R.string.currency),
                        listener?.sharedPreference()!!.stringConfig(SharedPreferenceConstants.CURRENCY))
                )
            }
            HELP_US -> {
                return listOf(
                    Settings(SettingsIds.ID_TRANSLATE, R.drawable.translate, getString(R.string.translate_app)),
                    Settings(SettingsIds.ID_SUGGESTIONS, R.drawable.suggestions, getString(R.string.suggestions)),
                    Settings(SettingsIds.ID_BUGS, R.drawable.bugs, getString(R.string.known_bugs))
                )
            }
            FOLLOW_US -> {
                return listOf(
                    Settings(SettingsIds.ID_TWITTER, R.drawable.twitter, getString(R.string.my_twitter)),
                    Settings(SettingsIds.ID_INSTAGRAM, R.drawable.instagram, getString(R.string.partner_instagram))
                )
            }
            APP_DETAILS -> {
                return listOf(
                    Settings(SettingsIds.ID_RATE, R.drawable.rateus, getString(R.string.rate_us)),

                    Settings(
                        SettingsIds.ID_VERSION,
                        R.drawable.version,
                        getString(R.string.app_version),
                        BuildConfig.VERSION_NAME)
                )
            }

        }

        return listOf()
    }

    override fun onItemClickListener(settingConfig: Settings) {
        viewModel.configureSettings(settingConfig)

    }

    private fun showCurrencies(currencies: List<Currency>){
        CurrencyAlertList(this, currencies, this).showAlert()
    }

    override fun onAlertClosedByOptionSelected() {
        setOptions()
    }
}