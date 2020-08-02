package com.mizanidev.deals.util

import android.content.Context

private const val PREFS_NAME: String = "DEALS"

class SharedPreferenceUtil(private val context: Context) {

    fun saveConfig(key: String, data: String){
        val settings = context.getSharedPreferences(PREFS_NAME, 0)
        val editor = settings.edit()
        editor.putString(key, data)
        editor.commit()
    }

    fun stringConfig(key: String): String{
        val settings = context.getSharedPreferences(PREFS_NAME, 0)
        return settings.getString(key, "")!!
    }

    fun saveConfig(key: String, data: Boolean){
        val settings = context.getSharedPreferences(PREFS_NAME, 0)
        val editor = settings.edit()
        editor.putBoolean(key, data)
        editor.commit()
    }

    fun booleanConfig(key: String): Boolean{
        val settings = context.getSharedPreferences(PREFS_NAME, 0)
        return settings.getBoolean(key, false)!!
    }

    fun removeConfig(key: String){
        val settings = context.getSharedPreferences(PREFS_NAME, 0)
        val editor = settings.edit()
        editor.remove(key)
        editor.commit()
    }
}