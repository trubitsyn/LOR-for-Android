/*
 * Copyright (C) 2015-2016 Nikola Trubitsyn
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package dev.trubitsyn.lorforandroid.ui.section.tracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView

import dev.trubitsyn.lorforandroid.R
import dev.trubitsyn.lorforandroid.ui.section.Item

internal class TrackerAdapter(private val items: List<Item>) : RecyclerView.Adapter<TrackerViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): TrackerViewHolder {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        val view = layoutInflater.inflate(R.layout.item_tracker, viewGroup, false)
        return TrackerViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: TrackerViewHolder, position: Int) {
        val item = items[position]
        viewHolder.title!!.text = item.title
        viewHolder.category!!.text = item.groupTitle
        viewHolder.tags!!.text = item.tags
        viewHolder.date!!.text = item.date
        viewHolder.author!!.text = item.author
        viewHolder.commentsCount!!.text = item.comments
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
