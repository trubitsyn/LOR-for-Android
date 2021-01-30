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

package dev.trubitsyn.lorforandroid

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.trubitsyn.lorforandroid.ui.section.forum.ForumOverviewDao
import dev.trubitsyn.lorforandroid.ui.section.forum.ForumOverviewItem
import dev.trubitsyn.lorforandroid.ui.section.forum.section.ForumSectionDao
import dev.trubitsyn.lorforandroid.ui.section.forum.section.ForumSectionItem
import dev.trubitsyn.lorforandroid.ui.section.gallery.GalleryDao
import dev.trubitsyn.lorforandroid.ui.section.gallery.GalleryItem
import dev.trubitsyn.lorforandroid.ui.section.news.NewsDao
import dev.trubitsyn.lorforandroid.ui.section.news.NewsItem
import dev.trubitsyn.lorforandroid.ui.section.tracker.TrackerDao
import dev.trubitsyn.lorforandroid.ui.section.tracker.TrackerItem
import dev.trubitsyn.lorforandroid.ui.topic.TopicDao
import dev.trubitsyn.lorforandroid.ui.topic.TopicItem

@Database(entities = [
    NewsItem::class,
    GalleryItem::class,
    TrackerItem::class,
    ForumOverviewItem::class,
    ForumSectionItem::class,
    TopicItem::class
], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract val newsDao: NewsDao
    abstract val galleryDao: GalleryDao
    abstract val trackerDao: TrackerDao
    abstract val forumOverviewDao: ForumOverviewDao
    abstract val forumSectionDao: ForumSectionDao
    abstract val topicDao: TopicDao
}