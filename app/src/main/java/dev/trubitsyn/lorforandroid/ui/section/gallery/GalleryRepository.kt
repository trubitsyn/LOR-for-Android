package dev.trubitsyn.lorforandroid.ui.section.gallery

interface GalleryRepository {
    fun getItems(filter: GalleryFilterEnum, offset: Int): List<GalleryItem>
}