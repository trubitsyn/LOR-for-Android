/*
 * Copyright (C) 2015-2021 Nikola Trubitsyn (getsmp)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package dev.trubitsyn.lorforandroid.ui.section.forum.section

import androidx.navigation.fragment.navArgs
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import dev.trubitsyn.lorforandroid.ui.base.BaseListFragment

@AndroidEntryPoint
class ForumSectionFragment : BaseListFragment() {
    private val args by navArgs<ForumSectionFragmentArgs>()

    override val adapter: PagingDataAdapter<*, RecyclerView.ViewHolder>
        get() = TODO()

    override fun onItemClickCallback(position: Int) {
        //(context as Callback).returnToActivity((items[position] as ForumSectionItem).url)
    }

    /*internal interface Callback {
        fun returnToActivity(url: String)
    }*/
}
