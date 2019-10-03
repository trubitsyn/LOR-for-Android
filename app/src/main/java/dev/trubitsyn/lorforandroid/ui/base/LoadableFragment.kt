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
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView

import butterknife.BindView
import dev.trubitsyn.lorforandroid.R

abstract class LoadableFragment : BaseFragment() {
    @BindView(R.id.progressBar)
    var progressBar: ProgressBar? = null
    @BindView(R.id.errorView)
    internal var errorView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        retainInstance = true
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.refresh, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.refreshButton -> {
                restart()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    protected open fun restart() {
        hideAllShowProgressView()
        resetState()
        fetchData()
    }

    private fun hideAllShowProgressView() {
        dataView()!!.visibility = View.GONE
        errorView!!.visibility = View.GONE
        progressBar!!.visibility = View.VISIBLE
    }

    protected open fun stopRefresh() {
        if (progressBar != null) progressBar!!.visibility = View.GONE
    }

    protected fun stopRefreshAndShow() {
        stopRefresh()
        if (dataView() != null) dataView()!!.visibility = View.VISIBLE
    }

    protected fun showErrorView(stringResource: Int) {
        stopRefresh()
        if (errorView != null) {
            dataView()!!.visibility = View.INVISIBLE
            errorView!!.visibility = View.VISIBLE
            errorView!!.setText(stringResource)
        }
    }

    protected open fun resetState() {
        // Implement in child classes
    }

    protected abstract fun fetchData()

    protected abstract fun dataView(): View?
}
