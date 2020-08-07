package com.mizanidev.deals.view.fragments.settings.currency

import android.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mizanidev.deals.R
import com.mizanidev.deals.model.others.Currency

class CurrencyAlertList(private val fragment: Fragment,
                        private val items: List<Currency>,
                        private val currencyCallback: CurrencyCallback) {

    fun showAlert() {
        val view = fragment.layoutInflater.inflate(R.layout.simple_list_view, null)
        val recyclerView: RecyclerView = view.findViewById(R.id.list)

        val dialogCurrency = AlertDialog.Builder(fragment.requireContext())
        dialogCurrency.setView(view)
        dialogCurrency.setTitle(R.string.select_currency)
        val dialog = dialogCurrency.create()
        dialog.show()

        recyclerView.adapter = CurrencyAdapter(
            fragment.requireContext(),
            items, dialog, currencyCallback)
        recyclerView.layoutManager = LinearLayoutManager(fragment.requireContext(), RecyclerView.VERTICAL,
            false)

    }

}