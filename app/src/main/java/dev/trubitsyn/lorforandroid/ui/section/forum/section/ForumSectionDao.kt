package dev.trubitsyn.lorforandroid.ui.section.forum.section

import androidx.room.Dao
import androidx.room.Query

@Dao
interface ForumSectionDao {
    @Query("SELECT * FROM forumsectionitem WHERE id = :id")
    fun findById(id: Long): ForumSectionItem
}