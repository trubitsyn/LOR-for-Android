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

import androidx.recyclerview.widget.RecyclerView

import com.loopj.android.http.RequestParams

import dev.trubitsyn.lorforandroid.ui.section.Item
import dev.trubitsyn.lorforandroid.ui.section.ItemFactory
import dev.trubitsyn.lorforandroid.ui.section.SectionFragment
import dev.trubitsyn.lorforandroid.ui.util.ItemClickCallback

class NewsFragment : SectionFragment() {

    override val itemsPerPage: Int
        get() = 20

    override val path: String
        get() = "news"

    override val requestParams: RequestParams
        get() = RequestParams("offset", offset)

    override fun getItemFactory(): ItemFactory {
        return NewsItemFactory()
    }

    override fun getMaxOffset(): Int {
        return 200
    }

    override fun getAdapter_(): RecyclerView.Adapter<*> {
        return NewsAdapter(items)
    }

    override fun onItemClickCallback(position: Int) {
        val item = items[position]
        val url: String
        if (item is MiniNewsItem) {
            url = (items[position] as MiniNewsItem).url
        } else if (item is Item) {
            url = (items[position] as Item).url
        } else
            throw ClassCastException("Object cannot be cast neither to MiniNewsItem nor to Item.")

        (context_ as ItemClickCallback).onTopicRequested(url)
    }

    companion object {
        const val TAG = "newsFragment"
    }
}
