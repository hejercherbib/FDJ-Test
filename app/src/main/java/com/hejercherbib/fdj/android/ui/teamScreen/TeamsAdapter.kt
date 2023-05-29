package com.hejercherbib.fdj.android.ui.teamScreen // ktlint-disable package-name

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.* // ktlint-disable no-wildcard-imports
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hejercherbib.fdj.android.R
import com.hejercherbib.fdj.android.model.Team

class TeamsAdapter() : ListAdapter<Team, TeamsAdapter.ViewHolder>(DiffCallback()) {

    class DiffCallback : DiffUtil.ItemCallback<Team>() {
        override fun areItemsTheSame(
            oldItem: Team,
            newItem: Team
        ): Boolean {
            return oldItem.idTeam == newItem.idTeam
        }

        override fun areContentsTheSame(
            oldItem: Team,
            newItem: Team
        ): Boolean {
            return oldItem == newItem
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val img: ImageView
        val context: Context

        init {
            context = view.context
            img = view.findViewById(R.id.imageView)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val viewHolder = ViewHolder(
            LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.item_league, viewGroup, false)
        )
        return viewHolder
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = getItem(position)
        Glide.with(viewHolder.img.context)
            .load(item.strTeamBadge)
            .placeholder(R.drawable.ic_placeholder_team)
            .error(R.drawable.ic_placeholder_team)
            .into(viewHolder.img)
    }
}
