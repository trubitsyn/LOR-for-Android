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

package dev.trubitsyn.lorforandroid.ui.section.news

import com.loopj.android.http.RequestParams
import dev.trubitsyn.lorforandroid.ui.section.Item
import dev.trubitsyn.lorforandroid.ui.section.SectionFragment
import dev.trubitsyn.lorforandroid.ui.util.ItemClickCallback

class NewsFragment : SectionFragment() {

    override val itemsPerPage = 20

    override val path = "news"

    override val requestParams: RequestParams
        get() = RequestParams("offset", offset)

    override val itemFactory = NewsItemFactory()

    override val maxOffset = 200

    override val adapter: NewsAdapter
        get() = NewsAdapter(items)

    override fun onItemClickCallback(position: Int) {
        val url = when (val item = items[position]) {
            is MiniNewsItem -> item.url
            is Item -> item.url
            else -> null
        } ?: throw ClassCastException("Object cannot be cast neither to MiniNewsItem nor to Item.")

        (context_ as ItemClickCallback).onTopicRequested(url)
    }

    companion object {
        const val TAG = "newsFragment"
    }
}
