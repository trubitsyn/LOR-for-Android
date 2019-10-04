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

package dev.trubitsyn.lorforandroid.ui.section.forum

import android.os.Bundle
import android.view.View
import android.widget.Toast

import androidx.recyclerview.widget.RecyclerView

import com.loopj.android.http.RequestParams

import dev.trubitsyn.lorforandroid.R
import dev.trubitsyn.lorforandroid.ui.section.ItemFactory
import dev.trubitsyn.lorforandroid.ui.section.SectionFragment
import dev.trubitsyn.lorforandroid.ui.util.ItemClickCallback
import dev.trubitsyn.lorforandroid.util.StringUtils

class ForumOverviewFragment : SectionFragment() {

    override val itemsPerPage: Int
        get() = 0

    override val path: String
        get() = "forum"

    override val requestParams: RequestParams?
        get() = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }

    override fun getMaxOffset(): Int {
        return 0
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipeRefreshLayout?.isEnabled = false
    }

    override fun getItemFactory(): ItemFactory {
        return ForumOverviewItemFactory()
    }

    override fun getAdapter_(): RecyclerView.Adapter<*> {
        return ForumOverviewAdapter(items as MutableList<ForumOverviewItem>)
    }

    override fun onItemClickCallback(position: Int) {
        val item = items[position] as ForumOverviewItem
        if (StringUtils.isClub(item.url)) {
            Toast.makeText(context_, R.string.error_access_denied, Toast.LENGTH_SHORT).show()
        } else {
            (context_ as ItemClickCallback).onForumSectionRequested(item.url, item.name)
        }
    }

    override fun loadMoreAllowed(): Boolean {
        return false
    }

    override fun showDividers(): Boolean {
        return false
    }

    companion object {
        const val TAG = "forumOverviewFragment"
    }
}
