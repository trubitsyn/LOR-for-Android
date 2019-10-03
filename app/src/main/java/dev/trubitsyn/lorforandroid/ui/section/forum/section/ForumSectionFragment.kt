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

package dev.trubitsyn.lorforandroid.ui.section.forum.section

import android.os.Bundle

import androidx.recyclerview.widget.RecyclerView

import com.loopj.android.http.RequestParams

import dev.trubitsyn.lorforandroid.ui.section.Item
import dev.trubitsyn.lorforandroid.ui.section.ItemFactory
import dev.trubitsyn.lorforandroid.ui.section.SectionFragment

class ForumSectionFragment : SectionFragment() {
    private var group: String? = null

    override val itemsPerPage: Int
        get() = 30

    override val path: String
        get() = "forum/" + group!!

    override val requestParams: RequestParams
        get() = RequestParams("offset", offset)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        group = arguments!!.getString("group")
    }

    override fun clearData() {
        offset = 0
        items.clear()
    }

    override fun getItemFactory(): ItemFactory {
        return ForumSectionItemFactory()
    }

    override fun getMaxOffset(): Int {
        return 300
    }

    override fun getAdapter_(): RecyclerView.Adapter<*> {
        return ForumSectionAdapter(items as MutableList<ForumSectionItem>)
    }

    override fun onItemClickCallback(position: Int) {
        (context_ as Callback).returnToActivity((items[position] as Item).url)
    }

    internal interface Callback {
        fun returnToActivity(url: String)
    }

    companion object {

        fun newInstance(group: String): ForumSectionFragment {
            val forumSectionFragment = ForumSectionFragment()
            val args = Bundle()
            args.putString("group", group)
            forumSectionFragment.arguments = args
            return forumSectionFragment
        }
    }
}
