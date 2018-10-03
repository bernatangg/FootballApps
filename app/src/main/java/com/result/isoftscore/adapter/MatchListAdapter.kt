package com.result.isoftscore.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import com.result.isoftscore.R
import com.result.isoftscore.model.Event
import com.result.isoftscore.util.dateFormatter
import com.result.isoftscore.util.timeFormatter
import com.result.isoftscore.util.visible
import kotlinx.android.synthetic.main.item_match.view.*


class MatchListAdapter(private val events: List<Event>,
                       private val clickListener: (Event) -> Unit):
        RecyclerView.Adapter<MatchListViewHolder>() {

    private var enableReminder = false
    private lateinit var reminderClickListener: (Event) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchListViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_match, parent, false)
        return MatchListViewHolder(itemView)
    }

    override fun getItemCount(): Int = events.size

    override fun onBindViewHolder(holder: MatchListViewHolder, position: Int) {
        val event = events[position]
        holder.bindItem(event, clickListener)

        if (enableReminder) {
            holder.bindReminder(event, reminderClickListener)
        }
    }

    fun enableReminderButton(clickListener: (Event) -> Unit) {
        enableReminder = true
        reminderClickListener = clickListener
    }

}

class MatchListViewHolder(view: View): RecyclerView.ViewHolder(view) {
    fun bindItem(event: Event, clickListener: (Event) -> Unit) {
        itemView.textview_matchlist_date.text = event.dateEvent.dateFormatter()
        itemView.textview_matchlist_time.text = event.time?.timeFormatter()
        itemView.textview_matchlist_hometeam.text = event.homeTeam
        itemView.textview_matchlist_awayteam.text = event.awayTeam
        itemView.textview_matchlist_homescore.text = event.homeScore
        itemView.textview_matchlist_awayscore.text = event.awayScore
        itemView.setOnClickListener { clickListener(event) }
    }

    fun bindReminder(event: Event, reminderClickListener: (Event) -> Unit) {
        itemView.imageview_matchlist_reminder.visible()
        itemView.imageview_matchlist_reminder.setOnClickListener { reminderClickListener(event) }
    }
}

