package com.result.isoftscore.activity

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.result.isoftscore.GlideApp
import com.result.isoftscore.R
import com.result.isoftscore.adapter.BasePageAdapter
import com.result.isoftscore.fragment.TeamDetailFragment
import com.result.isoftscore.fragment.TeamDetailOverviewFragment
import com.result.isoftscore.helper.database
import com.result.isoftscore.model.Team
import com.result.isoftscore.util.nullToEmpty
import kotlinx.android.synthetic.main.activity_team_detail.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_detail)

        val intent = intent
        team = intent.getParcelableExtra(EXTRA_TEAM_DATA)

        setupToolbar()
        setupViewPager()
        setupViewWithData()
        favoriteState()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.add_to_favorite -> {
                if (isFavorite) removeFromFavorite() else addToFavorite()

                isFavorite = !isFavorite
                setFavorite()
                true
            } else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar_teamdetail)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = ""
        }
    }

    private fun setupViewPager() {
        val pagerAdapter = BasePageAdapter(supportFragmentManager)
        pagerAdapter.addFragment(TeamDetailOverviewFragment
                .newInstance(team.descriptionEN.nullToEmpty()), getString(R.string.overview))
        pagerAdapter.addFragment(TeamDetailFragment
                .newInstance(team.idTeam), getString(R.string.player))
        viewpager_teamdetail.adapter = pagerAdapter
        tablayout_teamdetail.setupWithViewPager(viewpager_teamdetail)
    }

    private fun setupViewWithData() {
        GlideApp.with(this).load(team.teamBadge).into(imageview_teamdetail_badge)
        textview_teamdetail_name.text = team.team
        textview_teamdetail_formedyear.text = team.formedYear
        textview_teamdetail_stadium.text = team.stadium
    }

    private fun favoriteState() {
        database.use {
            val result = select(Team.TABLE_TEAM)
                    .whereArgs("(${Team.ID_TEAM} = {id})",
                            "id" to team.idTeam)
            val favorite = result.parseList(classParser<Team>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }

    private fun addToFavorite() {
        try {
            database.use { insert(Team.TABLE_TEAM,
                    Team.ID_TEAM to team.idTeam,
                    Team.TEAM to team.team,
                    Team.TEAM_SHORT to team.teamShort,
                    Team.ALTERNATE to team.alternate,
                    Team.FORMED_YEAR to team.formedYear,
                    Team.SPORT to team.sport,
                    Team.LEAGUE to team.league,
                    Team.ID_LEAGUE to team.idLeague,
                    Team.DIVISION to team.division,
                    Team.MANAGER to team.manager,
                    Team.STADIUM to team.stadium,
                    Team.KEYWORDS to team.keywords,
                    Team.STADIUM_THUMB to team.stadiumThumb,
                    Team.STADIUM_DESCRIPTION to team.stadiumDescription,
                    Team.STADIUM_LOCATION to team.stadiumLocation,
                    Team.STADIUM_CAPACITY to team.stadiumCapacity,
                    Team.WEBSITE to team.website,
                    Team.FACEBOOK to team.facebook,
                    Team.TWITTER to team.twitter,
                    Team.INSTAGRAM to team.instagram,
                    Team.DESCRIPTION_EN to team.descriptionEN,
                    Team.GENDER to team.gender,
                    Team.COUNTRY to team.country,
                    Team.TEAM_BADGE to team.teamBadge,
                    Team.TEAM_JERSEY to team.teamJersey,
                    Team.TEAM_LOGO to team.teamLogo,
                    Team.TEAM_BANNER to team.teamBanner,
                    Team.YOUTUBE to team.youtube)
            }
            snackbar(container_teamdetail, getString(R.string.add_to_fav)).show()
        } catch (e: SQLiteConstraintException) {
            snackbar(container_teamdetail, e.localizedMessage).show()
        }
    }

    private fun removeFromFavorite() {
        try {
            database.use {
                delete(Team.TABLE_TEAM, "(${Team.ID_TEAM} = {id})",
                        "id" to team.idTeam)
            }
            snackbar(container_teamdetail, getString(R.string.remove_from_fav)).show()
        } catch (e: SQLiteConstraintException) {
            snackbar(container_teamdetail, e.localizedMessage).show()
        }
    }

    private fun setFavorite() {
        if (isFavorite) menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_added_to_fav)
        else menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_add_to_fav)
    }
}

