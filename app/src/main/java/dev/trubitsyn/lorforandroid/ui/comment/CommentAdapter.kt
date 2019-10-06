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

package dev.trubitsyn.lorforandroid.ui.comment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import dev.trubitsyn.lorforandroid.R
import dev.trubitsyn.lorforandroid.api.model.Comment

class CommentAdapter : PagedListAdapter<Comment, CommentViewHolder>(diffCallback) {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): CommentViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_comment, viewGroup, false)
        return CommentViewHolder(view)
    }

    override fun onBindViewHolder(v: CommentViewHolder, position: Int) {
        v.setIsRecyclable(false)
        val comment = getItem(position) ?: return
        //CommentUtils.initView(comments, comment, context, v.replyTo, v.message, v.author, v.stars, v.date)
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Comment>() {
            override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean {
                return oldItem == newItem
            }
        }
    }
}