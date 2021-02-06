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

package dev.trubitsyn.lorforandroid.ui.section.tracker

import androidx.recyclerview.widget.RecyclerView
import dev.trubitsyn.lorforandroid.databinding.TrackerItemBinding

class TrackerViewHolder(
        private val binding: TrackerItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: TrackerItem) {
        binding.item = item
        binding.executePendingBindings()
    }
}
