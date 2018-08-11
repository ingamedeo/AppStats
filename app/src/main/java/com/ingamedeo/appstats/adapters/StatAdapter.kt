package com.ingamedeo.appstats.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ingamedeo.appstats.R
import com.ingamedeo.appstats.StatEntry
import kotlinx.android.synthetic.main.stat_list_item.view.*

class StatAdapter(val entries: List<StatEntry>, val context: Context): RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.stat_list_item, parent, false))
    }

    override fun getItemCount(): Int = entries.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.dayTextView?.text = entries[position].day
        holder.numUsersTextView?.text = entries[position].number.toString()
    }
}

class ViewHolder(val view: View): RecyclerView.ViewHolder(view) {
    val dayTextView = view.day
    val numUsersTextView = view.numUsers
}
