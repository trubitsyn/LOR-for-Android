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

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.DialogFragment

import dev.trubitsyn.lorforandroid.R
import dev.trubitsyn.lorforandroid.api.model.Comment

class CommentPreviewFragment : DialogFragment() {
    private val reply by lazy { view!!.findViewById<TextView>(R.id.commentReplyTo) }
    private val message by lazy { view!!.findViewById<TextView>(R.id.commentMessage) }
    val author by lazy { view!!.findViewById<TextView>(R.id.commentAuthor) }
    private val stars by lazy { view!!.findViewById<TextView>(R.id.commentStars) }
    val date by lazy { view!!.findViewById<TextView>(R.id.commentDate) }
    private var activity: Activity? = null
    private var comment: Comment? = null
    private var comments: List<Comment>? = null

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        this.activity = activity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    fun setComments(comments: List<Comment>, comment: Comment) {
        this.comments = comments
        this.comment = comment
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = View.inflate(activity, R.layout.comment_preview, null)
        CommentUtils.initView(comments!!, comment!!, context!!, reply, message, author, stars, date)
        val builder = AlertDialog.Builder(activity).setView(view)
        return builder.create()
    }

    companion object {
        const val TAG = "commentPreviewFragment"

        fun newInstance() = CommentPreviewFragment()
    }
}
