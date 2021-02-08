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

package dev.trubitsyn.lorforandroid.ui.section.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import dev.trubitsyn.lorforandroid.databinding.MiniNewsItemBinding
import dev.trubitsyn.lorforandroid.databinding.NewsItemBinding

class NewsAdapter constructor(
        private val viewModel: NewsViewModel
) : PagingDataAdapter<AbstractNewsItem, RecyclerView.ViewHolder>(Comparator) {

    override fun getItemViewType(position: Int) = when (getItem(position)) {
        is MiniNewsItem -> MINI
        is NewsItem -> FULL
        else -> -1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            MINI -> {
                val mini = MiniNewsItemBinding.inflate(inflater, parent, false)
                MiniNewsViewHolder(mini)
            }
            FULL -> {
                val full = NewsItemBinding.inflate(inflater, parent, false)
                NewsViewHolder(full)
            }
            else -> throw IllegalArgumentException()
        }
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        when (viewHolder.itemViewType) {
            MINI -> {
                val miniNewsItem = getItem(position) as MiniNewsItem
                (viewHolder as MiniNewsViewHolder).bind(miniNewsItem, viewModel)
            }
            FULL -> {
                val newsItem = getItem(position) as NewsItem
                (viewHolder as NewsViewHolder).bind(newsItem, viewModel)
            }
        }
    }

    private object Comparator : DiffUtil.ItemCallback<AbstractNewsItem>() {
        override fun areItemsTheSame(oldItem: AbstractNewsItem, newItem: AbstractNewsItem): Boolean {
            return when {
                oldItem is MiniNewsItem && newItem is MiniNewsItem -> {
                    oldItem.url == newItem.url
                }
                oldItem is NewsItem && newItem is NewsItem -> {
                    oldItem.url == newItem.url
                }
                else -> false
            }
        }

        override fun areContentsTheSame(oldItem: AbstractNewsItem, newItem: AbstractNewsItem): Boolean {
            return when {
                oldItem is MiniNewsItem && newItem is MiniNewsItem -> {
                    oldItem == newItem
                }
                oldItem is NewsItem && newItem is NewsItem -> {
                    oldItem == newItem
                }
                else -> false
            }
        }
    }

    private companion object {
        private const val MINI = 0
        private const val FULL = 1
    }
}
