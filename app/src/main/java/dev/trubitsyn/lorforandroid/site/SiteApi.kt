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

package dev.trubitsyn.lorforandroid.site

import dev.trubitsyn.lorforandroid.ui.section.forum.ForumOverviewItem
import dev.trubitsyn.lorforandroid.ui.section.forum.section.ForumSectionItem
import dev.trubitsyn.lorforandroid.ui.section.gallery.GalleryItem
import dev.trubitsyn.lorforandroid.ui.section.news.AbstractNewsItem
import dev.trubitsyn.lorforandroid.ui.section.tracker.TrackerItem
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SiteApi {

    @GET(value = "news/")
    suspend fun getNews(
            @Query(value = "offset") offset: Int
    ): List<AbstractNewsItem>

    @GET(value = "gallery/")
    suspend fun getGallery(
            @Query(value = "offset") offset: Int
    ): List<GalleryItem>

    @GET(value = "tracker/")
    suspend fun getTracker(
            @Query(value = "filter") filter: String,
            @Query(value = "offset") offset: Int
    ): List<TrackerItem>

    @GET(value = "forum/")
    suspend fun getForum(): List<ForumOverviewItem>

    @GET(value = "forum/{section}/")
    suspend fun getForumSection(
            @Path(value = "section") section: String,
            @Query(value = "offset") offset: Int
    ): List<ForumSectionItem>
}