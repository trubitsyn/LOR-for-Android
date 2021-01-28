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

package dev.trubitsyn.lorforandroid.ui.section.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.trubitsyn.lorforandroid.site.SiteApi
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
        private val newsDao: NewsDao,
        private val api: SiteApi
) : ViewModel() {

    val flow = Pager(
            PagingConfig(pageSize = 20, maxSize = 200)
    ) {
        NewsPagingSource(api)
    }.flow.cachedIn(viewModelScope)

    private val dataSource: DataSource.Factory<Int, NewsItem> = newsDao.newsByDate()

    private val config = PagedList.Config.Builder()
            .setPageSize(20)
            .setMaxSize(200)
            .build()

    val data: LiveData<PagedList<NewsItem>> = LivePagedListBuilder(dataSource, config).build()
}