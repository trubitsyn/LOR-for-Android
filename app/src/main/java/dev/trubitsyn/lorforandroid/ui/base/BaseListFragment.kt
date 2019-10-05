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

package dev.trubitsyn.lorforandroid.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import dev.trubitsyn.lorforandroid.R
import dev.trubitsyn.lorforandroid.ui.util.InfiniteScrollListener
import dev.trubitsyn.lorforandroid.ui.util.ItemClickListener

abstract class BaseListFragment : RefreshableFragment() {
    protected lateinit var context_: Context
    protected lateinit var adapter: RecyclerView.Adapter<*>
    private lateinit var scrollListener: InfiniteScrollListener
    protected val items: MutableList<Any> = mutableListOf()
    protected val recyclerView by lazy { view!!.findViewById<RecyclerView>(R.id.recyclerView)!! }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.context_ = context
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_swiperefresh_recyclerview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(context_)
        recyclerView.layoutManager = layoutManager
        if (showDividers) {
            recyclerView.addItemDecoration(DividerItemDecoration(context_, DividerItemDecoration.VERTICAL))
        }
        scrollListener = object : InfiniteScrollListener(layoutManager) {
            override fun onLoadMore() {
                if (loadMoreAllowed) fetchData()
            }
        }
        recyclerView.addOnScrollListener(scrollListener)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter = getAdapter_()
        recyclerView.adapter = adapter
        if (savedInstanceState != null) {
            stopRefreshAndShow()
        } else {
            fetchData()
        }
    }

    override fun resetState() {
        clearData()
        scrollListener.reset()
    }

    override fun restart() {
        recyclerView.scrollToPosition(0)
        super.restart()
    }

    protected fun showUserFriendlyError(errorString: Int) {
        if (items.isNotEmpty()) {
            Toast.makeText(context_, errorString, Toast.LENGTH_SHORT).show()
        } else
            showErrorView(errorString)
    }

    protected open val loadMoreAllowed = true

    protected open val showDividers = true

    protected open fun clearData() = items.clear()

    protected fun setOnClickListener(listener: ItemClickListener.OnItemClickListener) {
        recyclerView.addOnItemTouchListener(ItemClickListener(context_, listener))
    }

    protected abstract fun getAdapter_(): RecyclerView.Adapter<*>
}
