package com.result.isoftscore.activity

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import com.result.isoftscore.model.Team
import org.jetbrains.anko.startActivity

class TeamDetailActivity: AppCompatActivity() {
    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false
    private lateinit var team: Team

    companion object {
        const val EXTRA_TEAM_DATA = "team-data"
        fun start(context: Context, team: Team) {
            context.startActivity<TeamDetailActivity>(EXTRA_TEAM_DATA to team)
        }
    }


}