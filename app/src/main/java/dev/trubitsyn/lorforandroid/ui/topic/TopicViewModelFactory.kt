package dev.trubitsyn.lorforandroid.ui.topic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TopicViewModelFactory(private val url: String): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TopicViewModel(url) as T
    }
}