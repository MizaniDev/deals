package com.mizanidev.deals.view.fragments

import androidx.fragment.app.Fragment
import com.mizanidev.deals.util.SharedPreferenceUtil

abstract class BaseFragment : Fragment() {
    var listener: DealsInterface? = null
}

interface DealsInterface {
    fun sharedPreference(): SharedPreferenceUtil

}