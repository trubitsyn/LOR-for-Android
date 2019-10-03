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

package dev.trubitsyn.lorforandroid.ui.util

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class InfiniteScrollListener(private val layoutManager: LinearLayoutManager) : RecyclerView.OnScrollListener() {
    private val visibleThreshold = 5
    private var loading = true
    private var previousTotal = 0

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val visible = recyclerView.childCount
        val total = layoutManager.itemCount
        val firstVisible = layoutManager.findFirstVisibleItemPosition()

        if (loading && total > previousTotal) {
            if (visible + firstVisible >= total) {
                loading = false
                previousTotal = total
            }
        }

        if (!loading && total - visible <= firstVisible + visibleThreshold) {
            onLoadMore()
            loading = true
        }
    }

    abstract fun onLoadMore()

    fun reset() {
        loading = true
        previousTotal = 0
    }
}