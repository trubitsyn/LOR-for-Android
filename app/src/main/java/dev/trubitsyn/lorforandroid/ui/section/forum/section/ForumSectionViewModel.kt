package dev.trubitsyn.lorforandroid.ui.section.forum.section

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList

class ForumSectionViewModel(val factory: DataSource.Factory<Int, ForumSectionItem>) {
    private val config = PagedList.Config.Builder()
            .setPageSize(30)
            .setMaxSize(300)
            .build()

    val forumSectionTopics: LiveData<PagedList<ForumSectionItem>> =
            LivePagedListBuilder(factory, config).build()
}