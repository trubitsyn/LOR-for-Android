package dev.trubitsyn.lorforandroid.ui.section.news

import androidx.room.Dao
import androidx.room.Query

@Dao
interface NewsDao {
    @Query("SELECT * FROM newsitem WHERE id = :id")
    fun findById(id: Long): NewsItem
}