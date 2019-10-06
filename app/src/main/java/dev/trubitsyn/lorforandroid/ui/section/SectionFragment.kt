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

package dev.trubitsyn.lorforandroid.ui.section

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.loopj.android.http.RequestParams
import dev.trubitsyn.lorforandroid.R
import dev.trubitsyn.lorforandroid.ui.base.BaseListFragment
import dev.trubitsyn.lorforandroid.ui.util.ItemClickListener

abstract class SectionFragment<T> : BaseListFragment() {
    protected var offset: Int = 0

    abstract val itemsPerPage: Int

    abstract val path: String

    abstract val requestParams: RequestParams?

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListener(object : ItemClickListener.OnItemClickListener {
            override fun onItemClick(view: View) {
                onItemClickCallback(recyclerView.getChildAdapterPosition(view))
            }
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val viewModel = ViewModelProviders.of(this, ItemsViewModelFactory<T>(itemFactory, path, requestParams))
                .get(ItemsViewModel::class.java)
        viewModel.getItems().observe(this, Observer { items ->
            this.items.addAll(listOf(items))
            offset += itemsPerPage
            adapter.notifyDataSetChanged()
            stopRefreshAndShow()
        })
    }

    override fun fetchData() {
        if (offset <= maxOffset) {
            // get data
        } else
            Toast.makeText(context_, R.string.error_no_more_data, Toast.LENGTH_SHORT).show()
    }

    override fun clearData() {
        super.clearData()
        offset = 0
    }

    protected abstract val itemFactory: ItemFactory

    abstract val maxOffset: Int

    protected abstract fun onItemClickCallback(position: Int)
}
