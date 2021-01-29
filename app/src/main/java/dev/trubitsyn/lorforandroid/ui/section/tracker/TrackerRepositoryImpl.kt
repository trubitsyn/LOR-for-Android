/*
 * Copyright (C) 2015-2021 Nikola Trubitsyn (getsmp)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package dev.trubitsyn.lorforandroid.ui.section.tracker

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.RequestParams
import cz.msebera.android.httpclient.Header
import dev.trubitsyn.lorforandroid.HtmlResponseHandler
import org.jsoup.nodes.Element

class TrackerRepositoryImpl(
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
                    val items = factory.convert(responseBody)
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