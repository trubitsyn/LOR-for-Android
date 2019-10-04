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

import android.view.View
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView

import dev.trubitsyn.lorforandroid.R

class MiniNewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val title by lazy { itemView.findViewById<TextView>(R.id.miniNewsTitle) }
    val commentsCount by lazy { itemView.findViewById<TextView>(R.id.miniNewsCommentsCount) }
}