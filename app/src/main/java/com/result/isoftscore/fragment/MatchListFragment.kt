package com.result.isoftscore.fragment

import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.result.isoftscore.R
import com.result.isoftscore.R.color.colorAccent
import com.result.isoftscore.activity.MatchDetailActivity
import com.result.isoftscore.adapter.MatchListAdapter
import com.result.isoftscore.helper.BottomDecoration
import com.result.isoftscore.helper.LeagueData
import com.result.isoftscore.helper.MatchListType
import com.result.isoftscore.model.Event
import com.result.isoftscore.model.League
import com.result.isoftscore.presenter.MatchListPresenter
import com.result.isoftscore.util.dateTimeToMillis
import com.result.isoftscore.util.gone
import com.result.isoftscore.util.toPx
import com.result.isoftscore.view.MatchListView
import kotlinx.android.synthetic.main.fragment_list.view.*
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.support.v4.ctx
import java.util.concurrent.TimeUnit

class MatchListFragment: Fragment(), MatchListView, SwipeRefreshLayout.OnRefreshListener {

    private var events: MutableList<Event> = mutableListOf()
    private lateinit var presenter: MatchListPresenter
    private lateinit var adapter: MatchListAdapter
    private lateinit var rootView: View
    private lateinit var matchListType: MatchListType
    private var leagueId: String? = null

    companion object {
        const val ARG_MATCH_LIST_TYPE = "MATCH_LICT_TYPE"

        fun newInstance(matchListType: MatchListType): MatchListFragment {
            val fragment = MatchListFragment()
            val args = Bundle()
            args.putSerializable(ARG_MATCH_LIST_TYPE, matchListType)
            fragment.arguments = args

            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        matchListType = arguments?.getSerializable(ARG_MATCH_LIST_TYPE) as MatchListType
        if (matchListType == MatchListType.FAVORITES_MATCH) setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_list, container, false)
        rootView.swiperefresh_list.setOnRefreshListener(this)
        rootView.swiperefresh_list.setColorSchemeResources(colorAccent,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light)
        rootView.recyclerview_list.contentDescription = recyclerViewContentDescription(matchListType)
        rootView.recyclerview_list.layoutManager = LinearLayoutManager(ctx)
        rootView.recyclerview_list.addItemDecoration(BottomDecoration(16.toPx()))

        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter = MatchListAdapter(events) {
            MatchDetailActivity.start(ctx, it)
        }

        if (matchListType == MatchListType.NEXT_MATCH) {
            adapter.enableReminderButton { createEventOnCalendar(it) }
        }

        rootView.recyclerview_list.adapter = adapter
        if (matchListType !== MatchListType.FAVORITES_MATCH) {
            setupRecyclerViewHeader()
        } else {
            rootView.recyclerviewheader_list.gone()
        }

        presenter = MatchListPresenter(context, this)
    }

    private fun setupRecyclerViewHeader() {
        rootView.recyclerviewheader_list.attachTo(rootView.recyclerview_list)

        val spinnerAdapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item,
               LeagueData.provideLeagueData())
        rootView.spinner_list.adapter = spinnerAdapter
        rootView.spinner_list.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedLeague: League = rootView.spinner_list.selectedItem as League
                leagueId = selectedLeague.leagueId
                presenter.getMatchList(matchListType, leagueId)
            }
        }
    }

    private fun createEventOnCalendar(event: Event) {
        val intent = Intent(Intent.ACTION_INSERT)
        intent.type = "vnd.android.cursor.item/event"

        val dateTime = event.dateEvent + " " + event.time
        val startTime = dateTime.dateTimeToMillis()
        val endTime = startTime + TimeUnit.MINUTES.toMillis(90)

        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startTime)
        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime)
        intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, false)
        intent.putExtra(CalendarContract.Events.TITLE, "${event.homeTeam} vs ${event.awayTeam}")
        intent.putExtra(CalendarContract.Events.DESCRIPTION, "Reminder for match between ${event.homeTeam} vs ${event.awayTeam}")
        startActivity(intent)
    }

    private fun recyclerViewContentDescription(matchListType: MatchListType): String {
        return when (matchListType) {
            MatchListType.NEXT_MATCH -> getString(R.string.all_next_match)
            MatchListType.PREV_MATCH -> getString(R.string.all_prev_match)
            MatchListType.FAVORITES_MATCH -> getString(R.string.all_fav_match)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.fav_list_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.action_remove_all_favorites -> {
                alert("Remove all favorites match?") {
                    positiveButton("Yes") {
                        if (!events.isEmpty())
                            presenter.removeAllFavoriteMatch()
                    }
                    negativeButton("No") {}
                }.show()
                true
            } else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onStart() {
        super.onStart()
        if (matchListType == MatchListType.FAVORITES_MATCH) {
            presenter.getMatchList(matchListType, leagueId)
        }
    }

    override fun onDestroyView() {
        presenter.stopPresenting()
        super.onDestroyView()
    }

    override fun showLoading() {
        rootView.swiperefresh_list.isRefreshing = true
    }

    override fun hideLoading() {
        rootView.swiperefresh_list.isRefreshing = false
    }

    override fun showMatchList(data: List<Event>) {
        events.clear()
        events.addAll(data)
        adapter.notifyDataSetChanged()

    }

    override fun onRefresh() {
        presenter.getMatchList(matchListType, leagueId)
    }

}

