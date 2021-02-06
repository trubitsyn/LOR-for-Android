/*
 * Copyright (C) 2015-2021 Nikola Trubitsyn (getsmp)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package dev.trubitsyn.lorforandroid.ui.section.forum.section

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import dagger.hilt.android.qualifiers.ActivityContext
import dev.trubitsyn.lorforandroid.R
import javax.inject.Inject

class ForumSectionAdapter @Inject constructor(
        @ActivityContext private val context: Context
) : PagingDataAdapter<ForumSectionItem, ForumSectionViewHolder>(Comparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForumSectionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_forum_section, parent, false)
        return ForumSectionViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ForumSectionViewHolder, position: Int) {
        val item = getItem(position) ?: return
        viewHolder.apply {
            title.text = item.title
            pinned.visibility = if (item.isPinned) {
                View.VISIBLE
            } else {
                View.GONE
            }
            if (item.tags.isNotEmpty()) {
                tags.text = item.tags
                tags.visibility = View.VISIBLE
            } else {
                tags.visibility = View.GONE
            }
            replyFrom.text = item.author
            replyDate.text = item.date
            commentsCount.text = context.resources.getQuantityString(
                    R.plurals.comments,
                    item.comments,
                    item.comments
            )
        }
    }

    private object Comparator : DiffUtil.ItemCallback<ForumSectionItem>() {
        override fun areItemsTheSame(oldItem: ForumSectionItem, newItem: ForumSectionItem): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: ForumSectionItem, newItem: ForumSectionItem): Boolean {
            return oldItem == newItem
        }
    }
}
