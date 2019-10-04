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

import androidx.recyclerview.widget.RecyclerView

import dev.trubitsyn.lorforandroid.R
import dev.trubitsyn.lorforandroid.api.ApiManager
import dev.trubitsyn.lorforandroid.api.model.Comment
import dev.trubitsyn.lorforandroid.api.model.Comments
import dev.trubitsyn.lorforandroid.ui.base.BaseListFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommentFragment : BaseListFragment() {
    private var page: Int = 0
    private var previousCount = 0
    private var url: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        url = arguments!!.getString(ARG_URL)
    }

    override fun fetchData() {
        val comments = ApiManager.INSTANCE.apiComments.getComments(url!!, page)
        comments.enqueue(object : Callback<Comments> {
            override fun onResponse(call: Call<Comments>, response: Response<Comments>) {
                if (response.body() != null) {
                    if (response.body().comments!!.size > 0) {
                        val comments = response.body().comments
                        val commentsPerPage = 50

                        if (previousCount < commentsPerPage) {
                            // Add new comments to existing "page"
                            val itemSize = items.size
                            items.subList(itemSize - previousCount, itemSize).clear()
                            items.addAll(comments!!)
                        } else {
                            // Show new comments "page"
                            items.addAll(comments!!)
                        }

                        val commentSize = comments.size

                        // If loaded all comments at once, increment currentPage
                        if (commentSize == commentsPerPage) {
                            page++
                        }

                        previousCount = commentSize
                        adapter.notifyDataSetChanged()
                    } else if (response.body().comments!!.size == 0) {
                        showUserFriendlyError(R.string.error_no_comments)
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

    override fun getAdapter_(): RecyclerView.Adapter<*> {
        return CommentAdapter(items as List<Comment>, context_)
    }

    companion object {
        const val ARG_URL = "url"
        const val TAG = "commentFragment"

        fun newInstance(url: String): CommentFragment {
            val commentFragment = CommentFragment()
            val args = Bundle()
            args.putString(ARG_URL, url)
            commentFragment.arguments = args
            return commentFragment
        }
    }
}
