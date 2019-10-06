package dev.trubitsyn.lorforandroid.ui.section.gallery

import androidx.paging.PageKeyedDataSource

class GalleryDataSource(
        private val filter: Int
) : PageKeyedDataSource<Int, GalleryItem>() {
    val itemFactory = GalleryItemFactory()
    val path: String
        get() {
            val path = if (filter == GalleryFilterEnum.all.ordinal) "" else "/${GalleryFilterEnum.values()[filter].name}"
            return "gallery$path"
        }

    /*val requestParams: RequestParams
        get() = RequestParams("offset", offset)*/

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, GalleryItem>) {
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, GalleryItem>) {
        //
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, GalleryItem>) {
        //
    }
}