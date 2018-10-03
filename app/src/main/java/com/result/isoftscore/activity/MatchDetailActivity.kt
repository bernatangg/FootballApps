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
import com.result.isoftscore.helper.database
import com.result.isoftscore.model.Event
import com.result.isoftscore.presenter.MatchDetailPresenter
import com.result.isoftscore.util.dateFormatter
import com.result.isoftscore.util.nullToEmpty
import com.result.isoftscore.util.splitAndJoin
import com.result.isoftscore.view.MatchDetailView
import kotlinx.android.synthetic.main.activity_match_detail.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.startActivity

class MatchDetailActivity: AppCompatActivity(), MatchDetailView {
    private lateinit var event: Event
    private lateinit var presenter: MatchDetailPresenter
    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false

    companion object {
        const val EXTRA_EVENT_DATA = "event-data"
        fun start(context: Context, event: Event) {
            context.startActivity<MatchDetailActivity>(EXTRA_EVENT_DATA to event)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_detail)
        val intent = intent
        event = intent.getParcelableExtra(EXTRA_EVENT_DATA)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupViewWithData()
        favoriteState()

        presenter = MatchDetailPresenter(this)
        presenter.getHomeImage(event.idHomeTeam)
        presenter.getAwayImage(event.idAwayTeam)
    }

    private fun setupViewWithData() {
        textview_matchdetail_date.text = event.dateEvent.dateFormatter()

        textview_matchdetail_hometeam.text = event.homeTeam
        textview_matchdetail_homescore.text = event.homeScore
        textview_matchdetail_homeformation.text = event.homeFormation

        textview_matchdetail_awayteam.text = event.awayTeam
        textview_matchdetail_awayscore.text = event.awayScore
        textview_matchdetail_awayformation.text = event.awayFormation

        compare_matchdetail_goals.homeText = event.homeGoalDetails
                .nullToEmpty().splitAndJoin(";", ";\n")
        compare_matchdetail_goals.awayText = event.awayGoalDetails
                .nullToEmpty().splitAndJoin(";", ";\n")

        compare_matchdetail_shots.homeText = event.homeShots
        compare_matchdetail_shots.awayText = event.awayShots

        compare_matchdetail_goalkeeper.homeText = event.homeLineupGoalkeeper
                .nullToEmpty().splitAndJoin(";", ";\n")
        compare_matchdetail_goalkeeper.awayText = event.awayLineupGoalkeeper
                .nullToEmpty().splitAndJoin(";", ";\n")

        compare_matchdetail_defense.homeText = event.homeLineupDefense
                .nullToEmpty().splitAndJoin(";", ";\n")
        compare_matchdetail_defense.awayText = event.awayLineupDefense
                .nullToEmpty().splitAndJoin(";", ";\n")

        compare_matchdetail_middlefield.homeText = event.homeLineupMidfield
                .nullToEmpty().splitAndJoin(";", ";\n")
        compare_matchdetail_middlefield.awayText = event.awayLineupMidfield
                .nullToEmpty().splitAndJoin(";", ";\n")

        compare_matchdetail_forward.homeText = event.homeLineupForward
                .nullToEmpty().splitAndJoin(";", ";\n")
        compare_matchdetail_forward.awayText = event.awayLineupForward
                .nullToEmpty().splitAndJoin(";", ";\n")

        compare_matchdetail_substitutes.homeText = event.homeLineupSubstitutes
                .nullToEmpty().splitAndJoin(";", ";\n")
        compare_matchdetail_substitutes.awayText = event.awayLineupSubstitutes
                .nullToEmpty().splitAndJoin(";", ";\n")

    }

    private fun favoriteState() {
        database.use {
            val result = select(Event.TABLE_EVENT)
                    .whereArgs("(${Event.ID_EVENT} = {id})",
                            "id" to event.idEvent)
            val favorite = result.parseList(classParser<Event>())
            if (!favorite.isEmpty())
                isFavorite = true
        }
    }

    override fun showHomeImage(urlString: String) {
        GlideApp.with(this).load(urlString).into(imageview_matchdetail_homebadge)
    }

    override fun showAwayImage(urlString: String) {
        GlideApp.with(this).load(urlString).into(imageview_matchdetail_awaybadge)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.add_to_favorite -> {
                if (isFavorite)
                    removeFromFavorite() else addToFavorite()

                isFavorite = !isFavorite
                setFavorite()
                true
            } else -> super.onOptionsItemSelected(item)

        }
    }

    private fun setFavorite() {
        if (isFavorite) menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_added_to_fav)
        else menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_add_to_fav)

    }

    private fun addToFavorite() {
        try {
            database.use {
                insert(Event.TABLE_EVENT,
                       Event.ID_EVENT to event.idEvent,
                        Event.SPORT to event.sport,
                        Event.ID_LEAGUE to event.idLeague,
                        Event.LEAGUE to event.league,
                        Event.SEASON to event.season,
                        Event.HOME_TEAM to event.homeTeam,
                        Event.AWAY_TEAM to event.awayTeam,
                        Event.HOME_SCORE to event.homeScore,
                        Event.AWAY_SCORE to event.awayScore,
                        Event.HOME_GOAL_DETAILS to event.homeGoalDetails,
                        Event.HOME_RED_CARDS to event.homeRedCards,
                        Event.HOME_YELLOW_CARDS to event.homeYellowCards,
                        Event.HOME_LINEUP_GOALKEEPER to event.homeLineupGoalkeeper,
                        Event.HOME_LINEUP_DEFENSE to event.homeLineupDefense,
                        Event.HOME_LINEUP_MIDFIELD to event.homeLineupMidfield,
                        Event.HOME_LINEUP_FORWARD to event.homeLineupForward,
                        Event.HOME_LINEUP_SUBSTITUTES to event.homeLineupSubstitutes,
                        Event.HOME_FORMATION to event.homeFormation,
                        Event.AWAY_RED_CARDS to event.awayRedCards,
                        Event.AWAY_YELLOW_CARDS to event.awayYellowCards,
                        Event.AWAY_GOAL_DETAILS to event.awayGoalDetails,
                        Event.AWAY_LINEUP_GOALKEEPER to event.awayLineupGoalkeeper,
                        Event.AWAY_LINEUP_DEFENSE to event.awayLineupDefense,
                        Event.AWAY_LINEUP_MIDFIELD to event.awayLineupMidfield,
                        Event.AWAY_LINEUP_FORWARD to event.awayLineupForward,
                        Event.AWAY_LINEUP_SUBSTITUTES to event.awayLineupSubstitutes,
                        Event.AWAY_FORMATION to event.awayFormation,
                        Event.HOME_SHOTS to event.homeShots,
                        Event.AWAY_SHOTS to event.awayShots,
                        Event.DATE_EVENT to event.dateEvent,
                        Event.DATE to event.date,
                        Event.TIME to event.time,
                        Event.ID_HOME_TEAM to event.idHomeTeam,
                        Event.ID_AWAY_TEAM to event.idAwayTeam)
            }
            snackbar(matchdetail_container, getString(R.string.add_to_fav)).show()
        } catch (e: SQLiteConstraintException) {
            snackbar(matchdetail_container, e.localizedMessage).show()
        }
    }

    private fun removeFromFavorite() {
        try {
            database.use {
                delete(Event.TABLE_EVENT, "(${Event.ID_EVENT} = {id})",
                        "id" to event.idEvent)
            }
            snackbar(matchdetail_container, getString(R.string.remove_from_fav))
        } catch (e: SQLiteConstraintException) {
            snackbar(matchdetail_container, e.localizedMessage).show()
        }
    }

}

