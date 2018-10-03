package com.result.isoftscore.activity

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.result.isoftscore.GlideApp
import com.result.isoftscore.R
import com.result.isoftscore.model.Player
import com.result.isoftscore.util.gone
import com.result.isoftscore.util.nullToEmpty
import kotlinx.android.synthetic.main.activity_player_detail.*
import kotlinx.android.synthetic.main.content_player_detail.*
import org.jetbrains.anko.startActivity

class PlayerDetailActivity: AppCompatActivity() {
    private lateinit var player: Player

    companion object {
        const val EXTRA_PLAYER_DATA = "player-data"
        fun start(context: Context, player: Player) {
            context.startActivity<PlayerDetailActivity>(EXTRA_PLAYER_DATA to player)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_detail)

        val intent = intent
        player = intent.getParcelableExtra(EXTRA_PLAYER_DATA)

        setupToolbar()
        setupViewWithData()

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            } else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar_playerdetail)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = player.player
        }
    }

    private fun setupViewWithData() {
        setupFanArtImage()

        val playerWeight = player.weight.nullToEmpty()
        if (playerWeight.isEmpty() || playerWeight == "0"){
            container_playerdetail_weight.gone()
        } else {
            val playerWeightSplit = playerWeight.toLowerCase().split("kg")
            textview_playerdetail_weight.text = playerWeightSplit[0].trim()
        }

        val playerHeigth = player.height.nullToEmpty()
        if (playerHeigth.isEmpty() || playerHeigth == "") {
            container_playerdetail_height.gone()
        } else {
            val playerHeigthSplit = playerHeigth.toLowerCase().split("m")
            textview_playerdetail_height.text = playerHeigthSplit[0].trim()
        }

        textview_playerdetail_position.text = player.position
        textview_playerdetail_description.text = player.descriptionEN
    }

    private fun setupFanArtImage() {
        when {
            player.fanart != null -> {
                GlideApp.with(this).load(player.fanart).into(imageview_teamdetail_fanart)
            }
            player.thumb != null -> {
                GlideApp.with(this).load(player.thumb).into(imageview_teamdetail_fanart)
            } else -> parallax_container_playerdetail.gone()
        }
    }
}


