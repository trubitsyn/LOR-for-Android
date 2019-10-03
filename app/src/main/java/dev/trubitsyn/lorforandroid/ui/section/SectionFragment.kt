/*
 * Copyright (C) 2015-2016 Nikola Trubitsyn
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package dev.trubitsyn.lorforandroid.ui.section

import android.os.Bundle
import android.view.View
import android.widget.Toast

import com.loopj.android.http.AsyncHttpResponseHandler
import com.loopj.android.http.RequestParams

import org.jsoup.Jsoup

import java.io.UnsupportedEncodingException

import cz.msebera.android.httpclient.Header
import dev.trubitsyn.lorforandroid.R
import dev.trubitsyn.lorforandroid.ui.base.BaseListFragment
import dev.trubitsyn.lorforandroid.ui.util.ItemClickListener
import dev.trubitsyn.lorforandroid.util.NetworkClient
import java.nio.charset.Charset

abstract class SectionFragment : BaseListFragment() {
    protected var offset: Int = 0
    private val itemFactory = getItemFactory()
    private val maxOffset = getMaxOffset()
    private val handler = object : AsyncHttpResponseHandler() {
        override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
            var resp: String? = null
            try {
                resp = String(responseBody, Charset.forName("UTF-8"))
            } catch (e: UnsupportedEncodingException) {
                // Will never execute
            }

            try {
                val body = Jsoup.parse(resp!!).body()
                itemFactory.prepareItems(body, items)
            } catch (e: NullPointerException) {
                showUserFriendlyError(R.string.error_npe)
                return
            }

            offset += itemsPerPage
            adapter.notifyDataSetChanged()
            stopRefreshAndShow()
        }

        override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
            showUserFriendlyError(R.string.error_network)
        }
    }

    abstract val itemsPerPage: Int

    abstract val path: String

    abstract val requestParams: RequestParams?

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListener(object : ItemClickListener.OnItemClickListener {
            override fun onItemClick(view: View) {
                onItemClickCallback(recyclerView!!.getChildAdapterPosition(view))
            }
        })
    }

    override fun fetchData() {
        if (offset <= maxOffset) {
            NetworkClient.get("$path/", requestParams, handler)
        } else
            Toast.makeText(context_, R.string.error_no_more_data, Toast.LENGTH_SHORT).show()
    }

    override fun clearData() {
        super.clearData()
        offset = 0
    }

    protected abstract fun getItemFactory(): ItemFactory

    abstract fun getMaxOffset(): Int

    protected abstract fun onItemClickCallback(position: Int)
}
