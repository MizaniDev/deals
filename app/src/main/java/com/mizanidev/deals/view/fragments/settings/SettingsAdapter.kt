package com.mizanidev.deals.view.fragments.settings

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mizanidev.deals.R
import com.mizanidev.deals.model.generic.Settings
import com.mizanidev.deals.util.recyclerview.RecyclerViewSettingsListener
import kotlinx.android.synthetic.main.settings_row_string.view.*

class SettingsAdapter(private val context: Context, private val settings: List<Settings>,
                      private val recyclerViewSettingsListener: RecyclerViewSettingsListener
)
    : RecyclerView.Adapter<SettingsAdapter.ViewHolder>(){



    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val icon: ImageView = itemView.image_settings_row_icon
        val description: TextView = itemView.text_settings_row_description
        val selected: TextView = itemView.text_settings_row_selected
        val navigation: ImageView = itemView.image_settings_row_navigate

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.settings_row_string, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = settings.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val setting = settings[position]
        holder.icon.setImageResource(setting.imageView)
        holder.description.text = setting.description
        holder.selected.text = setting.selected
        holder.navigation.setImageResource(setting.navigate)

        holder.itemView?.setOnClickListener { recyclerViewSettingsListener.onItemClickListener(setting) }

    }
}