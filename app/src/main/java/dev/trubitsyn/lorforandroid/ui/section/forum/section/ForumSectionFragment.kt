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
import androidx.navigation.fragment.navArgs
import dev.trubitsyn.lorforandroid.ui.base.BaseListFragment

class ForumSectionFragment : BaseListFragment() {
    private val args by navArgs<ForumSectionFragmentArgs>()
    private var group: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        group = args.group
    }

    override val adapter: ForumSectionAdapter
        get() = ForumSectionAdapter()

    override fun onItemClickCallback(position: Int) {
        //(context as Callback).returnToActivity((items[position] as ForumSectionItem).url)
    }

    /*internal interface Callback {
        fun returnToActivity(url: String)
    }*/

    companion object {
        const val TAG = "forumSectionFragment"
    }
}
