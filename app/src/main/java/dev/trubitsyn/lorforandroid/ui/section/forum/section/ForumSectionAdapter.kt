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

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import dev.trubitsyn.lorforandroid.databinding.ForumSectionItemBinding

class ForumSectionAdapter constructor(
        private val viewModel: ForumSectionViewModel
) : PagingDataAdapter<ForumSectionItem, ForumSectionViewHolder>(Comparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForumSectionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ForumSectionItemBinding.inflate(inflater, parent, false)
        return ForumSectionViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ForumSectionViewHolder, position: Int) {
        val item = getItem(position) ?: return
        viewHolder.bind(item, viewModel)
    }

    private object Comparator : DiffUtil.ItemCallback<ForumSectionItem>() {
        override fun areItemsTheSame(oldItem: ForumSectionItem, newItem: ForumSectionItem): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: ForumSectionItem, newItem: ForumSectionItem): Boolean {
            return oldItem == newItem
        }
    }
}
