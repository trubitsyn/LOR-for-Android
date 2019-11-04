package dev.trubitsyn.lorforandroid.ui.topic

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TopicViewModel(val url: String) : ViewModel() {
    private val topic: MutableLiveData<TopicItem> by lazy {
        MutableLiveData<TopicItem>().also {
            loadTopic()
        }
    }

    fun getTopic(): LiveData<TopicItem> = topic

    private fun loadTopic() {
        // TODO
    }
}