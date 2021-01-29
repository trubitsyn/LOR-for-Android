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
import androidx.paging.PagedListAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import dev.trubitsyn.lorforandroid.R

class NewsAdapter : PagingDataAdapter<AbstractNewsItem, RecyclerView.ViewHolder>(diffCallback) {

    override fun getItemViewType(position: Int) = when (getItem(position)) {
        is MiniNewsItem -> MINI
        is NewsItem -> FULL
        else -> -1
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            MINI -> {
                val mini = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_mini_news, viewGroup, false)
                MiniNewsViewHolder(mini)
            }
            FULL -> {
                val full = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_news, viewGroup, false)
                NewsViewHolder(full)
            }
            else -> throw IllegalArgumentException()
        }
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, i: Int) {
        when (viewHolder.itemViewType) {
            MINI -> {
                val miniNewsItem = getItem(i) as MiniNewsItem
                (viewHolder as MiniNewsViewHolder).apply {
                    title.text = miniNewsItem.title
                    commentsCount.text = miniNewsItem.commentsCount
                }
            }
            FULL -> {
                val newsItem = getItem(i) as NewsItem
                (viewHolder as NewsViewHolder).apply {
                    title.text = newsItem.title
                    category.text = newsItem.groupTitle
                    tags.text = newsItem.tags
                    author.text = newsItem.author
                    date.text = newsItem.date
                    commentsCount.text = newsItem.comments
                }
            }
        }
    }

    companion object {
        private const val MINI = 0
        private const val FULL = 1

        private val diffCallback = object : DiffUtil.ItemCallback<AbstractNewsItem>() {
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
    }
}
