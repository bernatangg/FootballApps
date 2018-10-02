package com.result.isoftscore.activity

import android.content.Context
import android.support.v7.app.AppCompatActivity
import com.result.isoftscore.model.Player
import org.jetbrains.anko.startActivity

class PlayerDetailActivity: AppCompatActivity() {
    private lateinit var player: Player

    companion object {
        const val EXTRA_PLAYER_DATA = "player-data"
        fun start(context: Context, player: Player) {
            context.startActivity<PlayerDetailActivity>(EXTRA_PLAYER_DATA to player)
        }
    }
}


