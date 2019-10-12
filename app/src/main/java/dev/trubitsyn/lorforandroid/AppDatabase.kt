package dev.trubitsyn.lorforandroid

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.trubitsyn.lorforandroid.ui.section.forum.ForumDao
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

@Database(entities = [
    NewsItem::class,
    GalleryItem::class,
    TrackerItem::class,
    ForumOverviewItem::class,
    ForumSectionItem::class,
    TrackerItem::class
], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract val newsDao: NewsDao
    abstract val galleryDao: GalleryDao
    abstract val trackerDao: TrackerDao
    abstract val forumDao: ForumDao
    abstract val forumSectionDao: ForumSectionDao
    abstract val topicDao: TopicDao
}