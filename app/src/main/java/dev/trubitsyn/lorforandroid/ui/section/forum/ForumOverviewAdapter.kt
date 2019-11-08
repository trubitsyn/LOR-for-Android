/*
 * Copyright (C) 2015-2019 Nikola Trubitsyn
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

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.trubitsyn.lorforandroid.R


class ForumOverviewAdapter(
        private val sections: List<ForumOverviewItem>
) : RecyclerView.Adapter<ForumOverviewViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForumOverviewViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_simple, parent, false)
        return ForumOverviewViewHolder(view)
    }

    override fun onBindViewHolder(v: ForumOverviewViewHolder, position: Int) {
        v.itemName.text = sections[position].name
    }

    override fun getItemCount() = sections.size
}
