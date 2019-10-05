package dev.trubitsyn.lorforandroid.ui.topic

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.trubitsyn.lorforandroid.api.ApiManager
import dev.trubitsyn.lorforandroid.api.model.Topic
import dev.trubitsyn.lorforandroid.api.model.Topics
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TopicViewModel(val url: String) : ViewModel() {
    private val topic: MutableLiveData<Topic> by lazy {
        MutableLiveData<Topic>().also {
            loadTopic()
        }
    }

    fun getTopic(): LiveData<Topic> = topic

    private fun loadTopic() {
        val topics = ApiManager.INSTANCE.apiTopic.getTopic(url)
        topics.enqueue(object : Callback<Topics> {
            override fun onResponse(call: Call<Topics>, response: Response<Topics>) {
                response.body()?.let {
                    topic.value = it.topic ?: return@let null
                }
            }

            override fun onFailure(call: Call<Topics>, t: Throwable) {
            }
        })
    }
}