package dev.trubitsyn.lorforandroid.ui.section.tracker

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource


class TrackerDataSourceFactory(
        private val repository: TrackerRepository,
        private val filter: TrackerFilterEnum
) : DataSource.Factory<Int, TrackerItem>() {
    private val sourceLiveData = MutableLiveData<TrackerDataSource>()
    private lateinit var dataSource: TrackerDataSource

    override fun create(): DataSource<Int, TrackerItem> {
        dataSource = TrackerDataSource(repository, filter)
        sourceLiveData.postValue(dataSource)
        return dataSource
    }
}