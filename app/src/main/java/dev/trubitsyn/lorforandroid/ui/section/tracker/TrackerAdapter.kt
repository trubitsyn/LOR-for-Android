/*
 * Copyright (C) 2015-2019 Nikola Trubitsyn
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
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import dev.trubitsyn.lorforandroid.R

class TrackerAdapter : PagedListAdapter<TrackerItem, TrackerViewHolder>(diffCallback) {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): TrackerViewHolder {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        val view = layoutInflater.inflate(R.layout.item_tracker, viewGroup, false)
        return TrackerViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: TrackerViewHolder, position: Int) {
        val item = getItem(position) ?: return
        viewHolder.apply {
            title.text = item.title
            category.text = item.groupTitle
            tags.text = item.tags
            date.text = item.date
            author.text = item.author
            commentsCount.text = item.comments
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<TrackerItem>() {
            override fun areItemsTheSame(oldItem: TrackerItem, newItem: TrackerItem): Boolean {
                return oldItem.url == newItem.url
            }

            override fun areContentsTheSame(oldItem: TrackerItem, newItem: TrackerItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}
