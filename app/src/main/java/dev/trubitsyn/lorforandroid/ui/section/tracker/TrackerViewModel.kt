package dev.trubitsyn.lorforandroid.ui.section.tracker

import androidx.lifecycle.ViewModel
import androidx.paging.PagedList

class TrackerViewModel : ViewModel() {
    val config = PagedList.Config.Builder()
            .setPageSize(30)
            .setMaxSize(180)
            .build()

}