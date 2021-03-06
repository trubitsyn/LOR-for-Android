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

package dev.trubitsyn.lorforandroid.ui

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import dev.trubitsyn.lorforandroid.R
import dev.trubitsyn.lorforandroid.ui.base.BaseActivity

@AndroidEntryPoint
class MainActivity : BaseActivity(R.layout.activity_main) {
    private val bottomNavigationView by lazy { findViewById<BottomNavigationView>(R.id.bottom_navigation) }
    private val navController by lazy { findNavController(R.id.main_content) }
    private val appBarConfiguration by lazy {
        val destinations = setOf(
                R.id.news,
                R.id.gallery,
                R.id.tracker,
                R.id.forum,
                R.id.settings
        )
        AppBarConfiguration(destinations)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bottomNavigationView.setupWithNavController(navController)
        toolbar.setupWithNavController(navController, appBarConfiguration)
    }
}
