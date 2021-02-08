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

package dev.trubitsyn.lorforandroid.ui.section.forum.section

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import dev.trubitsyn.lorforandroid.site.SiteApi
import dev.trubitsyn.lorforandroid.ui.base.SelectionStateHandle

class ForumSectionViewModel constructor(
        private val api: SiteApi,
        private val selectionStateHandle: SelectionStateHandle<ForumSectionItem>
) : ViewModel(), SelectionStateHandle<ForumSectionItem> by selectionStateHandle {

    val flow = Pager(
            PagingConfig(pageSize = 20, maxSize = 200)
    ) {
        ForumSectionPagingSource(api)
    }.flow.cachedIn(viewModelScope)
}