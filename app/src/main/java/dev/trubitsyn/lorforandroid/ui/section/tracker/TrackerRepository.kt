package dev.trubitsyn.lorforandroid.ui.section.tracker

import androidx.lifecycle.LiveData

interface TrackerRepository {
    fun getItems(filter: TrackerFilterEnum, offset: Int): LiveData<List<TrackerItem>>
}