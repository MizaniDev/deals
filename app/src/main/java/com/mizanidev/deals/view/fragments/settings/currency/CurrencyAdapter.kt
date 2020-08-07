package com.mizanidev.deals.view.fragments.settings.currency

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mizanidev.deals.R
import com.mizanidev.deals.model.others.Currency
import com.mizanidev.deals.util.SharedPreferenceConstants
import com.mizanidev.deals.util.SharedPreferenceUtil
import kotlinx.android.synthetic.main.simple_row.view.*

class CurrencyAdapter(private val context: Context, private val currencies: List<Currency>,
                      private val dialog: AlertDialog, private val currencyCallback: CurrencyCallback):
    RecyclerView.Adapter<CurrencyAdapter.ViewHolder>() {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val title: TextView = itemView.simple_row_title
        val desc: TextView = itemView.simple_row_desc
        val row: LinearLayout = itemView.row

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.simple_row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = currencies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currency = currencies[position]
        holder.title.text = currency.code
        holder.desc.text = currency.name + " (" + currency.usd + ")"

        holder.row.setOnClickListener { saveCurrency(currency.code) }

    }

    private fun saveCurrency(code: String) {
        val sharedPreferenceUtil = SharedPreferenceUtil(context)
        sharedPreferenceUtil.saveConfig(SharedPreferenceConstants.CURRENCY, code)
        dialog.dismiss()
        currencyCallback.onAlertClosedByOptionSelected()
    }
}