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

package dev.trubitsyn.lorforandroid.ui.section.tracker

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.EntryPointAccessors
import dev.trubitsyn.lorforandroid.di.EntryPoints
import dev.trubitsyn.lorforandroid.ui.base.DefaultSelectionStateHandle

class TrackerViewModelFactory(
        context: Context,
        @TrackerFilter private val filter: String
) : ViewModelProvider.Factory {

    private val entryPoint = EntryPointAccessors.fromApplication(context, EntryPoints::class.java)

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TrackerViewModel::class.java)) {
            return TrackerViewModel(
                    api = entryPoint.api(),
                    selectionStateHandle = DefaultSelectionStateHandle(),
                    filter = filter
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}