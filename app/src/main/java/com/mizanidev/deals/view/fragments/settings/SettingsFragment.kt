package com.mizanidev.deals.view.fragments.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.billingclient.api.BillingFlowParams
import com.mizanidev.deals.BuildConfig
import com.mizanidev.deals.R
import com.mizanidev.deals.model.feedback.Suggestions
import com.mizanidev.deals.model.generic.KnowsBugs
import com.mizanidev.deals.model.others.Currency
import com.mizanidev.deals.model.generic.Settings
import com.mizanidev.deals.model.generic.SettingsIds
import com.mizanidev.deals.util.CToast
import com.mizanidev.deals.util.SharedPreferenceConstants
import com.mizanidev.deals.util.Util
import com.mizanidev.deals.view.fragments.BaseFragment
import com.mizanidev.deals.util.recyclerview.RecyclerViewSettingsListener
import com.mizanidev.deals.view.fragments.settings.currency.CurrencyAlertList
import com.mizanidev.deals.view.fragments.settings.currency.CurrencyCallback
import com.mizanidev.deals.viewmodel.DealsViewModel
import com.mizanidev.deals.viewmodel.ViewState
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

private const val REGION_VIEW = 1
private const val HELP_US = 2
private const val FOLLOW_US = 3
private const val APP_DETAILS = 4

class SettingsFragment: BaseFragment(),
    RecyclerViewSettingsListener, CurrencyCallback {
    private lateinit var settingRegion: RecyclerView
    private lateinit var settingHelp: RecyclerView
    private lateinit var settingFollow: RecyclerView
    private lateinit var settingApp: RecyclerView

    private val viewModel: DealsViewModel by sharedViewModel()
    private lateinit var util: Util

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponents(view)
        setOptions()
        observeViewModel()

        util = Util(requireContext())
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
        settingRegion.layoutManager =
            LinearLayoutManager(context,
                RecyclerView.VERTICAL, false)

        settingHelp.adapter = SettingsAdapter(
            requireContext(),
            showSettings(HELP_US),
            this
        )
        settingHelp.layoutManager =
            LinearLayoutManager(context,
                RecyclerView.VERTICAL, false)

        settingFollow.adapter = SettingsAdapter(
            requireContext(),
            showSettings(FOLLOW_US),
            this
        )
        settingFollow.layoutManager =
            LinearLayoutManager(context,
                RecyclerView.VERTICAL, false)

        settingApp.adapter = SettingsAdapter(
            requireContext(),
            showSettings(APP_DETAILS),
            this
        )
        settingApp.layoutManager =
            LinearLayoutManager(context,
                RecyclerView.VERTICAL, false)
    }

    private fun observeViewModel(){
        viewModel.viewState.observe(viewLifecycleOwner, Observer {
            when(it){
                is ViewState.ShowCurrencies -> showCurrencies(it.listCurrencies)
                is ViewState.ShowSuggestionAlert -> showSuggestionAlert()
                is ViewState.SuggestionError -> suggestionError()
                is ViewState.SuggestionSent -> suggestionSent()
                is ViewState.StartPurchase -> startPurchaseFlow()
                is ViewState.ShowBugs -> showBugs(it.items)
                is ViewState.NoBugs -> noBugs()
            }
        })
    }

    private fun showSettings(viewType: Int) : List<Settings>{
        when(viewType){
            REGION_VIEW -> {
                return listOf(
                    Settings(
                        SettingsIds.ID_CURRENCY,
                        R.drawable.currency,
                        getString(R.string.currency),
                        listener?.sharedPreference()!!.stringConfig(SharedPreferenceConstants.CURRENCY)),

                    Settings(SettingsIds.ID_PURCHASE,
                        R.drawable.region,
                        getString(R.string.remove_ads))
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

    private fun showSuggestionAlert() {
        val alertView = LayoutInflater.from(requireContext())
            .inflate(R.layout.fragment_suggestions, null)

        val editEmail: EditText = alertView.findViewById(R.id.edit_type_email)
        val editSuggestion: EditText = alertView.findViewById(R.id.edit_suggestion)

        val dialogView = AlertDialog.Builder(requireContext())
        dialogView.setView(alertView)
        dialogView.setTitle(R.string.suggestions)
        dialogView.setPositiveButton(R.string.send, null)
        val dialog = dialogView.create()
        alertView.setPadding(50, 0, 50, 0)
        dialog.show()

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            if(editSuggestion.text.isNotEmpty() && editEmail.text.isNotEmpty()) {
                val suggestions = Suggestions(
                    idUsuario = util.userID(),
                    email = editEmail.text.toString(),
                    message = editSuggestion.text.toString(),
                    dateTime = System.currentTimeMillis()
                )

                viewModel.sendSuggestion(suggestions)
                dialog.dismiss()
            }
        }
    }

    private fun suggestionError() {
        val cToast = CToast(requireContext())
        cToast.showError(getString(R.string.suggestion_error))
    }

    private fun suggestionSent() {
        val cToast = CToast(requireContext())
        cToast.showSuccess(getString(R.string.suggestion_sent))
    }

    private fun showBugs(bugs: ArrayList<KnowsBugs>) {
        val text = StringBuilder()
        for(bug: KnowsBugs in bugs) {
            if(bug.solved) {
                text.append(getString(R.string.bug_solved_description,
                    bug.title,
                    bug.description,
                    bug.versionSolved))
                text.append('\n')
                text.append("----------")
                text.append('\n')

            } else {
                text.append(getString(R.string.bug_notsolved_description,
                    bug.title,
                    bug.description))
                text.append('\n')
                text.append("----------")
                text.append('\n')
            }
        }



        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setTitle(R.string.known_bugs)
        alertDialog.setMessage(text.toString())
        alertDialog.setNeutralButton("ok", null)
        alertDialog.show()
    }

    private fun noBugs() {
        val cToast = CToast(requireContext())
        cToast.showInfo(getString(R.string.no_bugs_found))
    }

    private fun startPurchaseFlow() {
        //TODO implementar sistema de pagamento
//        val flowParams = BillingFlowParams.newBuilder()
//            .setSkuDetails(skuDetails)
//            .build()
//
//        val responseCode = billingClient
//            .launchBillingFlow(activity, flowParams).responseCode
    }
}