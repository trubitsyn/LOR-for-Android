package dev.trubitsyn.lorforandroid.ui.section.tracker

import androidx.paging.PageKeyedDataSource

class TrackerDataSource(
        private val repository: TrackerRepository,
        private val filter: TrackerFilterEnum
) : PageKeyedDataSource<Int, TrackerItem>() {
    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, TrackerItem>) {
        // TODO
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, TrackerItem>) {
        // TODO
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, TrackerItem>) {
        // TODO
    }
}