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

package dev.trubitsyn.lorforandroid.ui.section.gallery

import androidx.paging.PagingSource
import androidx.paging.PagingState
import dev.trubitsyn.lorforandroid.site.SiteApi

class GalleryPagingSource(
        private val api: SiteApi,
        @GalleryFilter private val filter: String
) : PagingSource<Int, GalleryItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GalleryItem> {
        val nextOffset = params.key ?: FIRST_PAGE_OFFSET
        try {
            val response = getResponse(nextOffset)
            return LoadResult.Page(
                    data = response,
                    prevKey = if (nextOffset == FIRST_PAGE_OFFSET) null else nextOffset - OFFSET,
                    nextKey = nextOffset + OFFSET
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, GalleryItem>): Int? {
        return null
    }

    private suspend fun getResponse(nextOffset: Int): List<GalleryItem> {
        return when (filter) {
            GalleryFilter.ALL -> api.getGallery(
                    offset = nextOffset
            )
            else -> api.getGallerySection(
                    section = filter,
                    offset = nextOffset
            )
        }
    }

    private companion object {
        private const val FIRST_PAGE_OFFSET = 0
        private const val OFFSET = 10
    }
}