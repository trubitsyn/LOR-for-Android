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

package dev.trubitsyn.lorforandroid.ui.section.forum.section

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView

import dev.trubitsyn.lorforandroid.R

class ForumSectionAdapter(private val items: List<ForumSectionItem>) : RecyclerView.Adapter<ForumSectionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForumSectionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_forum, parent, false)
        return ForumSectionViewHolder(view)
    }

    override fun onBindViewHolder(v: ForumSectionViewHolder, position: Int) {
        val item = items[position]
        v.title!!.text = item.title

        if (item.isPinned) {
            v.pinned!!.visibility = View.VISIBLE
        }

        if (item.tags == null || item.tags.length > 0) {
            v.tags!!.text = item.tags
        } else
            v.tags!!.visibility = View.GONE
        v.replyFrom!!.text = item.author
        v.replyDate!!.text = item.date
        v.commentsCount!!.text = item.comments
    }

    override fun getItemCount(): Int {
        return items.size
    }
}