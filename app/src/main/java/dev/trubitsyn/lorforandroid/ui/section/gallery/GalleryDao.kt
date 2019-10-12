package dev.trubitsyn.lorforandroid.ui.section.gallery

import androidx.room.Dao
import androidx.room.Query

@Dao
interface GalleryDao {
    @Query("SELECT * FROM galleryitem WHERE id = :id")
    fun findById(id: Long): GalleryItem
}