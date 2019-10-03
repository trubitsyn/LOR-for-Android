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

import dev.trubitsyn.lorforandroid.R
import dev.trubitsyn.lorforandroid.api.model.Comment
import dev.trubitsyn.lorforandroid.ui.base.ThemeActivity

class CommentActivity : ThemeActivity(), CommentClickListener {
    private var url: String? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comments)
        setupActionBar(this)

        if (savedInstanceState == null) {
            url = intent.getStringExtra("url")
            replace()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.refreshButton -> {
                replace()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun replace() {
        val commentFragment = CommentFragment.newInstance(url!!)
        supportFragmentManager.beginTransaction().replace(R.id.commentFragmentContainer, commentFragment).commit()
    }

    override fun showParent(comments: List<Comment>, parentComment: Comment) {
        val commentPreviewFragment = CommentPreviewFragment()
        commentPreviewFragment.setComments(comments, parentComment)
        commentPreviewFragment.show(supportFragmentManager, "commentPreviewFragment")
    }
}
