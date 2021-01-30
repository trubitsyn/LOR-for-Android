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

package dev.trubitsyn.lorforandroid.ui.base

import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.ViewFlipper
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import dev.trubitsyn.lorforandroid.R
import dev.trubitsyn.lorforandroid.ui.util.ItemClickListener
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

abstract class BaseListFragment : Fragment() {
    protected val swipeRefreshLayout by lazy { requireView().findViewById<SwipeRefreshLayout>(R.id.swipeRefreshLayout) }
    protected val recyclerView by lazy { requireView().findViewById<RecyclerView>(R.id.recyclerView)!! }
    protected val errorView by lazy { requireView().findViewById<TextView>(R.id.errorView) }
    private val viewFlipper by lazy { requireView().findViewById<ViewFlipper>(R.id.viewFlipper) }
    protected abstract val adapter: PagingDataAdapter<*, RecyclerView.ViewHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.refresh, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.refreshButton -> {
                adapter.refresh()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.adapter = adapter
        swipeRefreshLayout.setOnRefreshListener(adapter::refresh)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(view.context)
            if (showDividers) {
                addItemDecoration(DividerItemDecoration(view.context, DividerItemDecoration.VERTICAL))
            }
            addOnItemTouchListener(ItemClickListener(requireContext(), object : ItemClickListener.OnItemClickListener {
                override fun onItemClick(view: View) {
                    onItemClickCallback(recyclerView.getChildAdapterPosition(view))
                }
            }))
        }
        viewLifecycleOwner.lifecycleScope.launch {
            adapter.loadStateFlow.collectLatest {
                when (it.refresh) {
                    is LoadState.Loading -> {
                        viewFlipper.displayedChild = R.id.progressBar
                    }
                    is LoadState.NotLoading -> {
                        viewFlipper.displayedChild = R.id.swipeRefreshLayout
                    }
                    is LoadState.Error -> {
                        errorView.setText(R.string.error_network)
                        viewFlipper.displayedChild = R.id.errorView
                    }
                }
            }
        }
    }

    protected open val showDividers = true

    protected abstract fun onItemClickCallback(position: Int)
}
