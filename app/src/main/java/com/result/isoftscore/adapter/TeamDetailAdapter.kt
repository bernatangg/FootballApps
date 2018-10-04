package com.result.isoftscore.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.result.isoftscore.GlideApp
import com.result.isoftscore.R
import com.result.isoftscore.model.Player
import kotlinx.android.synthetic.main.item_player_detail.view.*

class TeamDetailAdapter(private val players: List<Player>,
                        private val clickListener: (Player) -> Unit):
        RecyclerView.Adapter<TeamDetailAdapterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamDetailAdapterViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_player_detail, parent, false)
        return TeamDetailAdapterViewHolder(itemView)
    }

    override fun getItemCount(): Int = players.size

    override fun onBindViewHolder(holder: TeamDetailAdapterViewHolder, position: Int) {
        holder.bindItem(players[position], clickListener)
    }
}

class TeamDetailAdapterViewHolder(view: View): RecyclerView.ViewHolder(view) {
    fun bindItem(player: Player, clickListener: (Player) -> Unit) {
        if (player.cutout != null) {
            GlideApp.with(itemView.context).load(player.cutout)
                    .into(itemView.imageview_teamdetail_player_profile)
        } else {
            GlideApp.with(itemView.context).load(R.drawable.ic_user)
                    .into(itemView.imageview_teamdetail_player_profile)
        }

        itemView.textview_teamdetail_player_name.text = player.player
        itemView.textview_teamdetail_player_position.text = player.position
        itemView.setOnClickListener{ clickListener(player) }
    }
}


