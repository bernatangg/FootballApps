package com.result.isoftscore.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.result.isoftscore.GlideApp
import com.result.isoftscore.R
import com.result.isoftscore.model.Team
import kotlinx.android.synthetic.main.item_team.view.*

class TeamListAdapter(private val teams: List<Team>,
                      private val clickListener: (Team) -> Unit):
        RecyclerView.Adapter<TeamListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamListViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_team, parent, false)
        return TeamListViewHolder(itemView)
    }

    override fun getItemCount(): Int = teams.size

    override fun onBindViewHolder(holder: TeamListViewHolder, position: Int) {
        holder.bindItem(teams[position], clickListener)
    }
}

class TeamListViewHolder(view: View): RecyclerView.ViewHolder(view) {
    fun bindItem(team: Team, clickListener: (Team) -> Unit) {
        GlideApp.with(itemView.context).load(team.teamBadge).into(itemView.imageview_teamlist_badge)
        itemView.textview_teamlist_name.text = team.team
        itemView.setOnClickListener { clickListener(team) }
    }
}

