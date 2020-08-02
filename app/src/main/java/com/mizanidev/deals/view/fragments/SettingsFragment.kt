package com.mizanidev.deals.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mizanidev.deals.R
import com.mizanidev.deals.model.utils.Settings
import com.mizanidev.deals.model.utils.SettingsIds

private const val REGION_VIEW = 1
private const val HELP_US = 2
private const val FOLLOW_US = 3
private const val APP_DETAILS = 4

class SettingsFragment: Fragment() {
    private lateinit var settingRegion: RecyclerView
    private lateinit var settingHelp: RecyclerView
    private lateinit var settingFollow: RecyclerView
    private lateinit var settingApp: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponents(view)
        setOptions()
    }

    private fun initComponents(view: View){
        settingRegion = view.findViewById(R.id.recycler_settings_region)
        settingHelp = view.findViewById(R.id.recycler_settings_help)
        settingFollow = view.findViewById(R.id.recycler_settings_follow)
        settingApp = view.findViewById(R.id.recycler_settings_details)
    }

    private fun setOptions(){
        settingRegion.adapter = SettingsAdapter(requireContext(), showSettings(REGION_VIEW))
        settingRegion.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        settingHelp.adapter = SettingsAdapter(requireContext(), showSettings(HELP_US))
        settingHelp.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        settingFollow.adapter = SettingsAdapter(requireContext(), showSettings(FOLLOW_US))
        settingFollow.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        settingApp.adapter = SettingsAdapter(requireContext(), showSettings(APP_DETAILS))
        settingApp.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
    }

    private fun showSettings(viewType: Int) : List<Settings>{
        when(viewType){
            REGION_VIEW -> {
                return listOf(
                    Settings(SettingsIds.ID_REGION, R.drawable.world_icon, getString(R.string.region)),
                    Settings(SettingsIds.ID_CURRENCY, R.drawable.world_icon, getString(R.string.currency))
                )
            }
            HELP_US -> {
                return listOf(
                    Settings(SettingsIds.ID_TRANSLATE, R.drawable.world_icon, getString(R.string.translate_app)),
                    Settings(SettingsIds.ID_SUGGESTIONS, R.drawable.world_icon, getString(R.string.suggestions)),
                    Settings(SettingsIds.ID_BUGS, R.drawable.world_icon, getString(R.string.known_bugs))
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
                    Settings(SettingsIds.ID_RATE, R.drawable.world_icon, getString(R.string.rate_us)),
                    Settings(SettingsIds.ID_VERSION, R.drawable.world_icon, getString(R.string.app_version))
                )
            }

        }

        return listOf()
    }
}