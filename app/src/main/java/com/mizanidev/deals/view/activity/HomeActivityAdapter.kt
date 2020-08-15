package com.mizanidev.deals.view.activity

import android.content.Context
import android.content.Intent
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StrikethroughSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mizanidev.deals.R
import com.mizanidev.deals.model.generalapi.GamesList
import com.mizanidev.deals.util.CountryFlags
import kotlinx.android.synthetic.main.recycler_view_main_row.view.*

class HomeActivityAdapter(private val context: Context, private var games: ArrayList<GamesList?>) :
    RecyclerView.Adapter<HomeActivityAdapter.ItemViewHolder>(){

    class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val image = itemView.img_game!!
        val title = itemView.txt_game_title!!
        val percent = itemView.txt_off!!
        val regularPrice = itemView.txt_price_original!!
        val discountPrice = itemView.txt_price_discount!!
        val flag = itemView.img_country_flag!!

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.recycler_view_main_row, parent, false)
        return ItemViewHolder(view)

    }

    override fun getItemCount(): Int = games.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
            val game = games[position]
            game?.let {
                holder.title.text = game.title
                holder.flag.text = CountryFlags.getCountryFlagByCountryCode(game.priceInfo?.country?.code)

                Glide.with(holder.itemView.context)
                    .load(game.imageUrl)
                    .override(80, 80)
                    .into(holder.image)

                if(game.priceInfo?.hasDiscount == true) {
                    holder.discountPrice.text = game.priceInfo.discountPrice?.discountPrice
                    holder.percent.text = game.priceInfo.discountPrice?.percentOff?.toString() + "% OFF"

                    val spannable = SpannableString(game.priceInfo.regularPrice?.regularPrice)
                    game.priceInfo.regularPrice?.regularPrice?.length?.let { it1 ->
                        spannable.setSpan(StrikethroughSpan(), 0, it1, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
                    }
                    holder.regularPrice.text = spannable
                    holder.regularPrice.setTextColor(context.resources.getColor(R.color.red))
                } else {
                    holder.discountPrice.visibility = View.GONE
                    holder.percent.visibility = View.GONE

                    holder.regularPrice.text = game.priceInfo?.regularPrice?.regularPrice
                }

                holder.itemView.setOnClickListener {
                    var intent = Intent(context, GameActivity::class.java)
                    intent.putExtra("slug", game.slug)
                    context?.startActivity(intent)
                }
            }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    fun addMore(dataViews: ArrayList<GamesList?>) {
        this.games.addAll(dataViews)
        notifyDataSetChanged()
    }

}