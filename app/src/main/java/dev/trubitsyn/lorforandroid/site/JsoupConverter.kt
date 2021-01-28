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

package dev.trubitsyn.lorforandroid.site

import okhttp3.MediaType
import okhttp3.ResponseBody
import okio.BufferedSource
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class JsoupConverter : Converter<ResponseBody, Element> {
    override fun convert(value: ResponseBody?): Element {
        return Jsoup.parse(value?.string()).body()
    }

    companion object {
        val FACTORY = object : Converter.Factory() {
            override fun responseBodyConverter(type: Type?, annotations: Array<out Annotation>?, retrofit: Retrofit?): Converter<ResponseBody, Element> {
                return JsoupConverter()
            }
        }
    }

    class HtmlResponseBody : ResponseBody() {
        override fun contentLength(): Long {
            TODO("not implemented")
        }

        override fun contentType(): MediaType? {
            TODO("not implemented")
        }

        override fun source(): BufferedSource {
            TODO("not implemented")
        }
    }
}