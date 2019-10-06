package dev.trubitsyn.lorforandroid.ui.section

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.loopj.android.http.AsyncHttpResponseHandler
import com.loopj.android.http.RequestParams
import cz.msebera.android.httpclient.Header
import dev.trubitsyn.lorforandroid.util.NetworkClient
import org.jsoup.Jsoup
import java.nio.charset.Charset

class ItemsViewModel<T>(
        private val path: String,
        private val requestParams: RequestParams?,
        private val itemFactory: ItemFactory
) : ViewModel() {
    val items: MutableLiveData<PagedList<T>> by lazy {
        MutableLiveData<PagedList<T>>().also {
            loadItems()
        }
    }

    private val handler = object : AsyncHttpResponseHandler() {
        override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
            val body = try {
                val resp = String(responseBody, Charset.forName("UTF-8"))
                Jsoup.parse(resp).body()
            } catch (e: Exception) {
                null
            }
            body?.let {
                val itemReceiver = mutableListOf<Any>()
                itemFactory.prepareItems(body, itemReceiver)
                //items.value = PagedList.Builder<K, V>(dataSource, config)
            }
        }

        override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
        }
    }

    fun loadItems() {
        NetworkClient.get("$path/", requestParams, handler)
    }
}