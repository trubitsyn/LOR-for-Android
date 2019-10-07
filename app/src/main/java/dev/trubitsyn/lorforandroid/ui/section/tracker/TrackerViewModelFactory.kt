package dev.trubitsyn.lorforandroid.ui.section.tracker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlin.properties.Delegates

class TrackerViewModelFactory(
        private val filter: TrackerFilterEnum
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        var repository: TrackerRepository by Delegates.notNull()
        return TrackerViewModel(repository, filter) as T
    }
}