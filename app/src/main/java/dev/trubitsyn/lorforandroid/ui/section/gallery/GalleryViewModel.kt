package dev.trubitsyn.lorforandroid.ui.section.gallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList

class GalleryViewModel(factory: DataSource.Factory<Int, GalleryItem>) : ViewModel() {
    private val config = PagedList.Config.Builder()
            .setPageSize(20)
            .setMaxSize(200)
            .build()

    val galleryTopics: LiveData<PagedList<GalleryItem>> =
            LivePagedListBuilder(factory, config)
                    .build()
}