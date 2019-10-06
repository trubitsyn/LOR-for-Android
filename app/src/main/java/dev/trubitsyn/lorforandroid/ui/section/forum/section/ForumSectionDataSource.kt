package dev.trubitsyn.lorforandroid.ui.section.forum.section

import android.content.Context
import androidx.paging.PageKeyedDataSource

class ForumSectionDataSource(
        private val context: Context,
        private val group: String
) : PageKeyedDataSource<Int, ForumSectionItem>() {
    val itemFactory
        get() = ForumSectionItemFactory(context)

    val path: String
        get() = "forum/$group"

    /*val requestParams: RequestParams
        get() = RequestParams("offset", offset)*/

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, ForumSectionItem>) {
        TODO("not implemented")
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, ForumSectionItem>) {
        TODO("not implemented")
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, ForumSectionItem>) {
        TODO("not implemented")
    }
}