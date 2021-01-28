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

package dev.trubitsyn.lorforandroid

import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import java.nio.charset.Charset

abstract class HtmlResponseHandler : AsyncHttpResponseHandler() {
    override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
        val body = try {
            val resp = String(responseBody, Charset.forName("UTF-8"))
            Jsoup.parse(resp).body()
        } catch (e: Exception) {
            null
        }
        onSuccess(statusCode, headers, body)
    }

    abstract fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: Element?)
}