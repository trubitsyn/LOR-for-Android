package dev.trubitsyn.lorforandroid.ui.section.tracker

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList

class TrackerViewModel(
        private val repository: TrackerRepository,
        private val filter: TrackerFilterEnum
) : ViewModel() {
    private val config = PagedList.Config.Builder()
            .setPageSize(30)
            .setMaxSize(180)
            .build()

    private val factory = TrackerDataSourceFactory(repository, filter)

    val trackerItems: LiveData<PagedList<TrackerItem>> =
            LivePagedListBuilder(factory, config).build()

}