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

import android.view.View
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView

import butterknife.BindView
import butterknife.ButterKnife
import dev.trubitsyn.lorforandroid.R

class CommentViewHolder(commentView: View) : RecyclerView.ViewHolder(commentView) {
    @BindView(R.id.commentAuthor)
    internal var author: TextView? = null
    @BindView(R.id.commentMessage)
    internal var message: TextView? = null
    @BindView(R.id.commentStars)
    internal var stars: TextView? = null
    @BindView(R.id.commentDate)
    internal var date: TextView? = null
    @BindView(R.id.commentReplyTo)
    internal var replyTo: TextView? = null

    init {
        ButterKnife.bind(this, commentView)
    }
}
