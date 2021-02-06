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

package dev.trubitsyn.lorforandroid.ui.section.forum

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.trubitsyn.lorforandroid.site.SiteApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForumViewModel @Inject constructor(
        private val api: SiteApi
) : ViewModel() {

    val flow = Pager(
            PagingConfig(pageSize = 20, maxSize = 200)
    ) {
        ForumPagingSource(api)
    }.flow.cachedIn(viewModelScope)

    private val _selectionState = MutableStateFlow<SelectionState>(SelectionState.Nothing)

    val selectionState: StateFlow<SelectionState> = _selectionState

    fun onItemSelected(item: ForumItem) {
        viewModelScope.launch {
            _selectionState.value = SelectionState.Item(item)
            delay(1000)
            _selectionState.value = SelectionState.Nothing
        }
    }

    sealed class SelectionState {
        object Nothing : SelectionState()
        data class Item(val item: ForumItem) : SelectionState()
    }
}