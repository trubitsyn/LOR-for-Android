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

import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

import butterknife.BindView
import butterknife.Unbinder
import dev.trubitsyn.lorforandroid.R

open class BaseActivity : AppCompatActivity() {
    @BindView(R.id.toolbarTop)
    var toolbar: Toolbar? = null
    protected var unbinder: Unbinder? = null
    protected var actionBar: ActionBar? = null

    override fun onDestroy() {
        super.onDestroy()
        unbinder!!.unbind()
    }

    protected fun setupActionBar(activity: AppCompatActivity) {
        activity.setSupportActionBar(toolbar)
        actionBar = activity.supportActionBar

        assert(actionBar != null)

        actionBar!!.setDisplayHomeAsUpEnabled(true)
    }
}
