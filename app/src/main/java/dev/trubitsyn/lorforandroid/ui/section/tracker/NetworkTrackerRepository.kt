package dev.trubitsyn.lorforandroid.ui.section.tracker

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.RequestParams
import cz.msebera.android.httpclient.Header
import dev.trubitsyn.lorforandroid.HtmlResponseHandler
import org.jsoup.nodes.Element

class NetworkTrackerRepository(
        private val client: AsyncHttpClient,
        private val factory: TrackerItemFactory
) : TrackerRepository {
    override fun getItems(filter: TrackerFilterEnum, offset: Int): LiveData<List<TrackerItem>> {
        val liveList = MutableLiveData<List<TrackerItem>>()
        val params = RequestParams().apply {
            put("filter", filter.name)
            put("offset", offset)
        }
        client.get("tracker/", params, object : HtmlResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: Element?) {
                val list = mutableListOf<Any>()
                responseBody?.let {
                    factory.prepareItems(responseBody, list)
                }
                liveList.postValue(list.toList() as List<TrackerItem>?)
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?, error: Throwable?) {
                // TODO
            }
        })
        return liveList
    }
}