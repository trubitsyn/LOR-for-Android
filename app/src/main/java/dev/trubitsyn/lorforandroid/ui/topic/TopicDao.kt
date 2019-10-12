package dev.trubitsyn.lorforandroid.ui.topic

import androidx.room.Dao
import androidx.room.Query

@Dao
interface TopicDao {
    @Query("SELECT * FROM topicitem WHERE id = :id")
    fun findById(id: Long): TopicItem
}