package com.mizanidev.deals.model.generic

import com.mizanidev.deals.R

data class Settings(
    val idSettings: Int,
    val imageView: Int,
    val description: String = "",
    val selected: String = "",
    val navigate: Int = R.drawable.ic_right_arrow
)

class SettingsIds{
    companion object {
        const val ID_PURCHASE = 1
        const val ID_CURRENCY = 2
        const val ID_TRANSLATE = 3
        const val ID_SUGGESTIONS = 4
        const val ID_BUGS = 5
        const val ID_TWITTER = 6
        const val ID_INSTAGRAM = 7
        const val ID_RATE = 8
        const val ID_VERSION = 9
    }
}