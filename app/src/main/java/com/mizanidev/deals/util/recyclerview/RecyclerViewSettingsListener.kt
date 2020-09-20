package com.mizanidev.deals.util.recyclerview

import com.mizanidev.deals.model.generic.Settings

interface RecyclerViewSettingsListener {
    fun onItemClickListener(settings: Settings)
}