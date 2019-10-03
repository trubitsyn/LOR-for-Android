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

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import java.util.ArrayList

import butterknife.BindView
import butterknife.ButterKnife
import dev.trubitsyn.lorforandroid.R
import dev.trubitsyn.lorforandroid.ui.util.DividerItemDecoration
import dev.trubitsyn.lorforandroid.ui.util.InfiniteScrollListener
import dev.trubitsyn.lorforandroid.ui.util.ItemClickListener

abstract class BaseListFragment : RefreshableFragment() {
    protected var context: Context
    protected var adapter: RecyclerView.Adapter<*>
    private var scrollListener: InfiniteScrollListener? = null
    protected val items: MutableList<*> = ArrayList()
    @BindView(R.id.recyclerView)
    var recyclerView: RecyclerView? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.context = context
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_swiperefresh_recyclerview, container, false)
        unbinder = ButterKnife.bind(this, view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(context)
        recyclerView!!.layoutManager = layoutManager
        if (showDividers()) recyclerView!!.addItemDecoration(DividerItemDecoration(context))

        scrollListener = object : InfiniteScrollListener(layoutManager) {
            override fun onLoadMore() {
                if (loadMoreAllowed()) fetchData()
            }
        }

        recyclerView!!.addOnScrollListener(scrollListener!!)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (savedInstanceState != null) {
            initAdapter()
            stopRefreshAndShow()
        } else {
            initAdapter()
            fetchData()
        }
    }

    override fun resetState() {
        clearData()
        scrollListener!!.reset()
    }

    override fun restart() {
        recyclerView!!.scrollToPosition(0)
        super.restart()
    }

    protected fun showUserFriendlyError(errorString: Int) {
        if (items.size > 0) {
            Toast.makeText(context, errorString, Toast.LENGTH_SHORT).show()
        } else
            showErrorView(errorString)
    }

    private fun initAdapter() {
        adapter = getAdapter()
        recyclerView!!.adapter = adapter
    }

    protected open fun loadMoreAllowed(): Boolean {
        return true
    }

    protected open fun showDividers(): Boolean {
        return true
    }

    protected open fun clearData() {
        items.clear()
    }

    protected fun setOnClickListener(listener: ItemClickListener.OnItemClickListener) {
        recyclerView!!.addOnItemTouchListener(ItemClickListener(context, listener))
    }

    protected abstract fun getAdapter(): RecyclerView.Adapter<*>
}
