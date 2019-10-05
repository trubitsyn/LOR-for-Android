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
import androidx.navigation.fragment.findNavController
import com.loopj.android.http.RequestParams
import dev.trubitsyn.lorforandroid.R
import dev.trubitsyn.lorforandroid.ui.section.SectionFragment
import dev.trubitsyn.lorforandroid.util.StringUtils

class ForumOverviewFragment : SectionFragment() {

    override val itemsPerPage = 0

    override val path = "forum"

    override val requestParams: RequestParams? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }

    override val maxOffset = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipeRefreshLayout?.isEnabled = false
    }

    override val itemFactory = ForumOverviewItemFactory()

    override val adapter: ForumOverviewAdapter
        get() = ForumOverviewAdapter(items as MutableList<ForumOverviewItem>)

    override fun onItemClickCallback(position: Int) {
        val item = items[position] as ForumOverviewItem
        if (StringUtils.isClub(item.url)) {
            Toast.makeText(context_, R.string.error_access_denied, Toast.LENGTH_SHORT).show()
        } else {
            val action = ForumOverviewFragmentDirections.actionForumOverviewToForumSection(
                    group = item.url,
                    name = item.name
            )
            findNavController().navigate(action)
        }
    }

    override val loadMoreAllowed = false

    override val showDividers = false

    companion object {
        const val TAG = "forumOverviewFragment"
    }
}
