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
import android.view.View
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

import butterknife.BindView
import dev.trubitsyn.lorforandroid.R

abstract class RefreshableFragment : LoadableFragment() {
    @BindView(R.id.swipeRefreshLayout)
    var swipeRefreshLayout: SwipeRefreshLayout? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipeRefreshLayout!!.setOnRefreshListener {
            resetState()
            errorView!!.visibility = View.GONE
            fetchData()
        }
    }

    override fun stopRefresh() {
        super.stopRefresh()
        // swipeRefreshLayout still might be null
        if (swipeRefreshLayout != null) swipeRefreshLayout!!.isRefreshing = false
    }

    override fun dataView(): View? {
        return swipeRefreshLayout
    }
}
