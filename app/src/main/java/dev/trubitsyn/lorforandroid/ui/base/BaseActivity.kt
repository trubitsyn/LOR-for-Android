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
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.preference.PreferenceManager
import dev.trubitsyn.lorforandroid.R

abstract class BaseActivity : AppCompatActivity() {
    protected val toolbar by lazy { findViewById<Toolbar>(R.id.toolbarTop)!! }

    override fun onCreate(savedInstanceState: Bundle?) {
        val isDarkTheme = PreferenceManager.getDefaultSharedPreferences(this).getBoolean(getString(R.string.pref_dark_theme), false)
        val nightMode = if (isDarkTheme) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        AppCompatDelegate.setDefaultNightMode(nightMode)
        super.onCreate(savedInstanceState)
    }

    protected fun setupActionBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
