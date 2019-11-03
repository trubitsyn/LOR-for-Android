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

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.trubitsyn.lorforandroid.R
import dev.trubitsyn.lorforandroid.ui.util.ItemClickListener

abstract class BaseListFragment : LoadableFragment() {
    protected val recyclerView by lazy { view!!.findViewById<RecyclerView>(R.id.recyclerView)!! }
    protected abstract val adapter: RecyclerView.Adapter<*>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_swiperefresh_recyclerview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(view.context)
        recyclerView.layoutManager = layoutManager
        if (showDividers) {
            recyclerView.addItemDecoration(DividerItemDecoration(view.context, DividerItemDecoration.VERTICAL))
        }
        setOnClickListener(object : ItemClickListener.OnItemClickListener {
            override fun onItemClick(view: View) {
                onItemClickCallback(recyclerView.getChildAdapterPosition(view))
            }
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recyclerView.adapter = adapter
        if (savedInstanceState != null) {
            stopRefreshAndShow()
        }
    }

    override fun restart() {
        recyclerView.scrollToPosition(0)
        super.restart()
    }

    protected fun showUserFriendlyError(errorString: Int) {
        val hasData = false//items.isNotEmpty()
        if (hasData) {
            Toast.makeText(context, errorString, Toast.LENGTH_SHORT).show()
        } else {
            showErrorView(errorString)
        }
    }

    protected open val showDividers = true

    protected abstract fun onItemClickCallback(position: Int)

    private fun setOnClickListener(listener: ItemClickListener.OnItemClickListener) {
        recyclerView.addOnItemTouchListener(ItemClickListener(context!!, listener))
    }
}
