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

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.util.*

class JsoupParser private constructor(): HtmlParser {
    private val adapters  = WeakHashMap<Class<*>, DocumentAdapter<*>>()

    override fun parse(document: String): Document {
        return Jsoup.parse(document)
    }

    override fun <T> fromDocument(document: Document, clazz: Class<T>): T? {
        val adapter = getAdapter<T>(clazz)
        return adapter?.fromDocument(document) as? T
    }

    override fun <T> getAdapter(clazz: Class<T>): DocumentAdapter<*>? {
        return adapters[clazz]
    }

    class Builder {
        private val instance = JsoupParser()

        fun registerDocumentAdapter(adapter: DocumentAdapter<*>, clazz: Class<*>) = apply {
            instance.adapters[clazz] = adapter
        }

        fun build() = instance
    }
}