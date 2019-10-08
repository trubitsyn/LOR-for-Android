package dev.trubitsyn.lorforandroid.ui.section.forum

import androidx.room.Dao
import androidx.room.Query

@Dao
interface ForumDao {
    @Query("SELECT * FROM forumoverviewitem")
    fun getAll(): List<ForumOverviewItem>
}