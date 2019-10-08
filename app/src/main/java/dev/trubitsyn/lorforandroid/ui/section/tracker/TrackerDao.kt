package dev.trubitsyn.lorforandroid.ui.section.tracker

import androidx.room.Dao
import androidx.room.Query

@Dao
interface TrackerDao {
    @Query("SELECT * FROM trackeritem WHERE id = :id")
    fun findById(id: Long): TrackerItem
}