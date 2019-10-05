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
import dev.trubitsyn.lorforandroid.api.ApiManager
import dev.trubitsyn.lorforandroid.api.model.Comment
import dev.trubitsyn.lorforandroid.api.model.Comments
import dev.trubitsyn.lorforandroid.ui.base.BaseListFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommentFragment : BaseListFragment(), CommentClickListener {
    private var page: Int = 0
    private var previousCount = 0
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
                fetchData()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showParent(comments: List<Comment>, parentComment: Comment) {
        val commentPreviewFragment = CommentPreviewFragment.newInstance(comments, parentComment)
        commentPreviewFragment.show(childFragmentManager, CommentPreviewFragment.TAG)
    }

    override fun fetchData() {
        val call = ApiManager.INSTANCE.apiComments.getComments(url, page)
        call.enqueue(object : Callback<Comments> {
            override fun onResponse(call: Call<Comments>, response: Response<Comments>) {
                response.body()?.let {
                    it.comments?.let {
                        when (it.size) {
                            0 -> showUserFriendlyError(R.string.error_no_comments)
                            else -> {
                                if (previousCount < COMMENTS_PER_PAGE) {
                                    // Add new comments to existing "page"
                                    items.subList(items.size - previousCount, items.size).clear()
                                    items.addAll(it)
                                } else {
                                    // Show new comments "page"
                                    items.addAll(it)
                                }

                                // If loaded all comments at once, increment currentPage
                                if (it.size == COMMENTS_PER_PAGE) {
                                    page++
                                }
                                previousCount = it.size
                                adapter.notifyDataSetChanged()
                            }
                        }
                    }
                }
                stopRefreshAndShow()
            }

            override fun onFailure(call: Call<Comments>, t: Throwable) {
                showUserFriendlyError(R.string.error_network)
            }
        })
    }

    override fun clearData() {
        super.clearData()
        page = 0
        previousCount = 0
    }

    override val adapter: CommentAdapter
        get() = CommentAdapter(items as List<Comment>, context_)

    companion object {
        const val TAG = "commentFragment"

        private const val COMMENTS_PER_PAGE = 50
    }
}
