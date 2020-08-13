package com.mizanidev.deals.view.activity

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.mizanidev.deals.R
import com.mizanidev.deals.model.game.SingleGameRequestInfo
import com.mizanidev.deals.model.utils.Util
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

    private val viewModel: DealsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val slug = intent.extras?.get("slug") as String

        initComponents()
        configureViewModel(slug)
        setObservables()
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
    }

    private fun configureViewModel(slug: String){
        viewModel.requestGame(slug)
    }

    private fun setObservables(){
        viewModel.viewState.observe(this, Observer {
            when(it){
                is ViewState.ShowGame -> showGame(it.game!!)
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

        txtGameReleaseDate.text = getString(R.string.release_date, gameReceived.releaseDate)
        Util.boldDescriptionText(txtGameReleaseDate)

        if(gameReceived.hasDemo){
            txtGameHasDemo.text = getString(R.string.has_demo, getString(R.string.yes))
        }else{
            txtGameHasDemo.text = getString(R.string.has_demo, getString(R.string.no))
        }

        Util.boldDescriptionText(txtGameHasDemo)

        txtGameSize.text = getString(R.string.game_size, Util.gameSizeReadable(gameReceived.size))
        Util.boldDescriptionText(txtGameSize)

        txtGamePublishers.text = getString(R.string.publishers, gameReceived.publishers?.get(0)?.name)
        Util.boldDescriptionText(txtGamePublishers)

        txtNumberOfPlayers.text = getString(R.string.number_of_players, gameReceived.numberOfPlayers)
        Util.boldDescriptionText(txtNumberOfPlayers)


        if(gameReceived.priceInfo?.physicalRelease == true){
            txtHasPhysicalEdition.text = getString(R.string.physical_media, getString(R.string.yes))
        }else{
            txtHasPhysicalEdition.text = getString(R.string.physical_media, getString(R.string.no))
        }

        Util.boldDescriptionText(txtHasPhysicalEdition)

        txtBestPrice.text = getString(R.string.best_price, gameReceived.priceInfo?.currentPrice)
        Util.boldDescriptionText(txtBestPrice)

        txtCountry.text = getString(R.string.country, gameReceived.priceInfo?.country?.name)
        Util.boldDescriptionText(txtCountry)

        txtStatus.text = getString(R.string.game_status, gameReceived.priceInfo?.status)
        Util.boldDescriptionText(txtStatus)

        txtGoldPoints.text = getString(R.string.gold_points, gameReceived.priceInfo?.goldPoints?.toString())
        Util.boldDescriptionText(txtGoldPoints)

        buttonDescription.setOnClickListener { showDescription(gameReceived) }
        buttonCategories.setOnClickListener { showCategories(gameReceived) }
        buttonLanguages.setOnClickListener { showLanguages(gameReceived) }
        buttonSeeTrailer.setOnClickListener {  }
        //TODO call youtube
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
        Util.showSimpleAlert(this, final)
    }

    private fun showCategories(gameReceived: SingleGameRequestInfo){
        val stringBuilder = StringBuilder()
        gameReceived.categories.let {
            stringBuilder.append(it).append(", ")
        }

        var final = stringBuilder.toString().replace("[", "")
        final = final.replace("],", "")

        Util.showSimpleAlert(this, final)
    }

    private fun showDescription(gameReceived: SingleGameRequestInfo){
        Util.showSimpleAlert(this, gameReceived.description)
    }
}