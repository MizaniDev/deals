package com.mizanidev.deals.view.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.google.android.gms.ads.*
import com.mizanidev.deals.BuildConfig
import com.mizanidev.deals.R
import com.mizanidev.deals.model.game.SingleGameRequestInfo
import com.mizanidev.deals.model.generic.AdMobError
import com.mizanidev.deals.util.Url
import com.mizanidev.deals.util.Util
import com.mizanidev.deals.viewmodel.DealsViewModel
import com.mizanidev.deals.viewmodel.ViewState
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.StringBuilder

class GameActivity : AppCompatActivity() {
    private lateinit var progressGame: LinearLayout
    private lateinit var imageGame: ImageView
    private lateinit var imagePlatform: ImageView
    private lateinit var buttonLanguages: Button
    private lateinit var buttonCategories: Button
    private lateinit var buttonDescription: Button
    private lateinit var buttonSeeTrailer: Button
    private lateinit var txtGameTitle: TextView
    private lateinit var txtGamePrice: TextView
    private lateinit var txtGameReleaseDate: TextView
    private lateinit var txtGameHasDemo: TextView
    private lateinit var txtGameSize: TextView
    private lateinit var txtGamePublishers: TextView
    private lateinit var txtNumberOfPlayers: TextView
    private lateinit var txtHasPhysicalEdition: TextView
    private lateinit var txtBestPrice: TextView
    private lateinit var txtCountry: TextView
    private lateinit var txtStatus: TextView
    private lateinit var txtGoldPoints: TextView

    private lateinit var util: Util
    private val viewModel: DealsViewModel by viewModel()

    //Ads
    private lateinit var mInterstitialAd: InterstitialAd

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        MobileAds.initialize(this)

        val slug = intent.extras?.get("slug") as String

        initComponents()
        configureViewModel(slug)
        setObservables()
        startAds()
    }

    private fun initComponents(){
        progressGame = findViewById(R.id.game_progress)
        imageGame = findViewById(R.id.img_game_image)
        imagePlatform = findViewById(R.id.img_game_platform_logo)
        buttonLanguages = findViewById(R.id.button_game_languages)
        buttonCategories = findViewById(R.id.button_game_categories)
        buttonDescription = findViewById(R.id.button_game_description)
        buttonSeeTrailer = findViewById(R.id.button_game_trailer)
        txtGameTitle = findViewById(R.id.txt_game_title)
        txtGamePrice = findViewById(R.id.txt_game_price)
        txtGameReleaseDate = findViewById(R.id.txt_game_releasedate)
        txtGameHasDemo = findViewById(R.id.txt_game_hasdemo)
        txtGameSize = findViewById(R.id.txt_game_size)
        txtGamePublishers = findViewById(R.id.txt_game_publisher)
        txtNumberOfPlayers = findViewById(R.id.txt_game_players)
        txtHasPhysicalEdition = findViewById(R.id.txt_game_physical)
        txtBestPrice = findViewById(R.id.txt_game_bestprice)
        txtCountry = findViewById(R.id.txt_game_country)
        txtStatus = findViewById(R.id.txt_game_status)
        txtGoldPoints = findViewById(R.id.txt_game_goldpoints)

        util = Util(this)
    }

    private fun configureViewModel(slug: String){
        viewModel.requestGame(slug)
    }

    private fun setObservables(){
        viewModel.viewState.observe(this, Observer {
            when(it){
                is ViewState.ShowGame -> showGame(it.game)
                is ViewState.Loading -> showLoading()
                is ViewState.Loaded -> hideLoading()
            }
        })
    }

    private fun showLoading(){
        progressGame.visibility = View.VISIBLE
    }

    private fun hideLoading(){
        progressGame.visibility = View.GONE
    }

    private fun showGame(gameReceived: SingleGameRequestInfo){
        Glide.with(this)
            .load(gameReceived.imageUrl)
            .override(200, 200)
            .into(imageGame)

        Glide.with(this)
            .load(R.drawable.nintendo)
            .override(35, 35)
            .into(imagePlatform)

        txtGameTitle.text = gameReceived.title
        txtGamePrice.text = gameReceived.priceInfo?.currentPrice

        txtGameReleaseDate.text = getString(R.string.release_date, util.nullToNoInfo(gameReceived.releaseDate))
        util.boldDescriptionText(txtGameReleaseDate)

        if(gameReceived.hasDemo){
            txtGameHasDemo.text = getString(R.string.has_demo, getString(R.string.yes))
        }else{
            txtGameHasDemo.text = getString(R.string.has_demo, getString(R.string.no))
        }

        util.boldDescriptionText(txtGameHasDemo)

        txtGameSize.text = getString(R.string.game_size, util.gameSizeReadable(gameReceived.size))
        util.boldDescriptionText(txtGameSize)

        try {
            txtGamePublishers.text = getString(R.string.publishers,
                util.nullToNoInfo(gameReceived.publishers?.get(0)?.name))
            util.boldDescriptionText(txtGamePublishers)
        } catch (exception: Exception) {
            txtGamePublishers.text = getString(R.string.publishers,
                util.nullToNoInfo(null))
            util.boldDescriptionText(txtGamePublishers)
        }

        txtNumberOfPlayers.text = getString(R.string.number_of_players,
            util.nullToNoInfo(gameReceived.numberOfPlayers))
        util.boldDescriptionText(txtNumberOfPlayers)

        if(gameReceived.priceInfo?.physicalRelease == true){
            txtHasPhysicalEdition.text = getString(R.string.physical_media, getString(R.string.yes))
        }else{
            txtHasPhysicalEdition.text = getString(R.string.physical_media, getString(R.string.no))
        }

        util.boldDescriptionText(txtHasPhysicalEdition)

        txtBestPrice.text = getString(R.string.best_price, util.nullToNoInfo(gameReceived.priceInfo?.currentPrice))
        util.boldDescriptionText(txtBestPrice)

        txtCountry.text = getString(R.string.country,
            util.nullToNoInfo(gameReceived.priceInfo?.country?.name))
        util.boldDescriptionText(txtCountry)

        txtStatus.text = getString(R.string.game_status, util.nullToNoInfo(gameReceived.priceInfo?.status))
        util.boldDescriptionText(txtStatus)

        txtGoldPoints.text = getString(R.string.gold_points,
            util.nullToNoInfo(gameReceived.priceInfo?.goldPoints?.toString()))
        util.boldDescriptionText(txtGoldPoints)

        buttonDescription.setOnClickListener { showDescription(gameReceived) }
        buttonCategories.setOnClickListener { showCategories(gameReceived) }
        buttonLanguages.setOnClickListener { showLanguages(gameReceived) }
        buttonSeeTrailer.setOnClickListener { showYoutubeTrailer(gameReceived) }
    }

    private fun showLanguages(gameReceived: SingleGameRequestInfo){
        val stringBuilder = StringBuilder()
        gameReceived.languages?.forEach {
            stringBuilder.append(it.region).append(":\n")
            it.languages?.forEach{itRegion ->
                stringBuilder.append(itRegion.name).append(", ")
            }
            stringBuilder.append("%#")
            stringBuilder.append("\n\n")
        }

        val final = stringBuilder.toString().replace(", %#", "")
        util.showSimpleAlert(final)
    }

    private fun showCategories(gameReceived: SingleGameRequestInfo){
        val stringBuilder = StringBuilder()
        gameReceived.categories.let {
            stringBuilder.append(it).append(", ")
        }

        var final = stringBuilder.toString().replace("[", "")
        final = final.replace("],", "")

        util.showSimpleAlert(final)
    }

    private fun showDescription(gameReceived: SingleGameRequestInfo){
        util.showSimpleAlert(util.nullToNoInfo(gameReceived.description))
    }

    private fun showYoutubeTrailer(gameReceived: SingleGameRequestInfo) {
        val videoId = gameReceived.youtubeId
        if(videoId != null) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(Url.youtubeId().plus(videoId)))
            intent.putExtra("VIDEO_ID", videoId)
            startActivity(intent)
        }

    }

    private fun startAds() {
        if(!Util.APP_PURCHASED) {
            configAds()
            showAds()
        }

    }

    private fun configAds() {
        mInterstitialAd = InterstitialAd(this)

        if(BuildConfig.DEBUG) {
            mInterstitialAd.adUnitId = getString(R.string.interstitial_ad_unit_id)
        }else mInterstitialAd.adUnitId = getString(R.string.interstitial_ad_unit_id_prod)

        mInterstitialAd.loadAd(AdRequest.Builder().build())
    }

    private fun showAds() {
        mInterstitialAd.adListener = object: AdListener() {
            override fun onAdLoaded() {
                mInterstitialAd.show()
            }

            override fun onAdFailedToLoad(p0: LoadAdError?) {
                super.onAdFailedToLoad(p0)
                if(p0?.message != null) {
                    val adMobError = AdMobError(p0.message)
                    viewModel.registerErrorOnAd(this@GameActivity, adMobError)
                }

            }
        }
    }

}