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

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.navigation.fragment.navArgs
import dev.trubitsyn.lorforandroid.R
import dev.trubitsyn.lorforandroid.api.model.Comment
import dev.trubitsyn.lorforandroid.ui.base.BaseListFragment

class CommentFragment : BaseListFragment(), CommentClickListener {
    private lateinit var url: String
    private val args by navArgs<CommentFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        url = args.url
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.refreshButton -> {
                resetState()
                errorView!!.visibility = View.GONE
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showParent(comments: List<Comment>, parentComment: Comment) {
        val commentPreviewFragment = CommentPreviewFragment.newInstance(comments, parentComment)
        commentPreviewFragment.show(childFragmentManager, CommentPreviewFragment.TAG)
    }

    override fun onItemClickCallback(position: Int) {
        //
    }

    override val adapter: CommentAdapter
        get() = CommentAdapter()

    companion object {
        const val TAG = "commentFragment"
    }
}
