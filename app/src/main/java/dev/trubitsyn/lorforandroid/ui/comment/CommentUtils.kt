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

import android.content.Context
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.TextView

import dev.trubitsyn.lorforandroid.R
import dev.trubitsyn.lorforandroid.api.model.Comment
import dev.trubitsyn.lorforandroid.util.DateUtils
import dev.trubitsyn.lorforandroid.util.StringUtils

internal object CommentUtils {
    fun initView(comments: List<Comment>, comment: Comment, context: Context, reply: TextView, message: TextView, author: TextView, stars: TextView, date: TextView) {
        author.text = comment.author!!.nick
        message.text = StringUtils.removeLineBreak(Html.fromHtml(comment.processedMessage))
        message.movementMethod = LinkMovementMethod.getInstance()
        stars.text = comment.author.stars
        date.text = DateUtils.getDate(comment.postdate!!)
        if (comment.reply != null) {
            val parent = comments.parentOfReply(comment.reply.id!!)
            reply.text = context.getString(R.string.replyTo, parent!!.author!!.nick)
            reply.setOnClickListener { (context as CommentClickListener).showParent(comments, parent) }
        } else reply.visibility = View.GONE
    }

    private fun List<Comment>.parentOfReply(reply: Int) = firstOrNull { it.id == reply }
}
