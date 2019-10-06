package dev.trubitsyn.lorforandroid.ui.section.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList

class NewsViewModel(factory: DataSource.Factory<Int, NewsItem>) : ViewModel() {
    private val config = PagedList.Config.Builder()
            .setPageSize(20)
            .setMaxSize(200)
            .build()

    val news: LiveData<PagedList<NewsItem>> =
            LivePagedListBuilder(factory, config).build()
}