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

package dev.trubitsyn.lorforandroid.ui.topic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.trubitsyn.lorforandroid.site.SiteApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class TopicViewModel(
        api: SiteApi,
        private val url: TopicUrl,
) : ViewModel() {

    val flow = flow {
        _loadState.value = LoadState.Loading
        val response = try {
            api.getTopic(
                    section = url.section,
                    group = url.group,
                    id = url.id
            )
        } catch (e: Exception) {
            null
        }
        _loadState.value = when (response) {
            null -> LoadState.Error
            else -> LoadState.NotLoading.also {
                emit(response)
            }
        }
    }

    private val _loadState = MutableStateFlow<LoadState>(LoadState.NotLoading)

    private val _isProgressVisible = MutableStateFlow(true)

    private val _isContentVisible = MutableStateFlow(false)

    private val _isErrorVisible = MutableStateFlow(false)

    val isProgressVisible = _isProgressVisible.asStateFlow()

    val isContentVisible = _isContentVisible.asStateFlow()

    val isErrorVisible = _isErrorVisible.asStateFlow()

    init {
        viewModelScope.launch {
            _loadState.collectLatest {
                when (it) {
                    is LoadState.Loading -> {
                        _isProgressVisible.emit(true)
                        _isContentVisible.emit(false)
                        _isErrorVisible.emit(false)
                    }
                    is LoadState.NotLoading -> {
                        _isProgressVisible.emit(false)
                        _isContentVisible.emit(true)
                        _isErrorVisible.emit(false)
                    }
                    is LoadState.Error -> {
                        _isProgressVisible.emit(false)
                        _isContentVisible.emit(false)
                        _isErrorVisible.emit(true)
                    }
                }
            }
        }
    }

    sealed class LoadState {
        object Loading : LoadState()
        object NotLoading : LoadState()
        object Error : LoadState()
    }
}