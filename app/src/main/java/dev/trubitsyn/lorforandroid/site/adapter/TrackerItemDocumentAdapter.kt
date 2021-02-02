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

package dev.trubitsyn.lorforandroid.site.adapter

import androidx.core.text.parseAsHtml
import dev.trubitsyn.lorforandroid.site.DocumentAdapter
import dev.trubitsyn.lorforandroid.ui.section.tracker.TrackerItem
import dev.trubitsyn.lorforandroid.util.StringUtils
import org.jsoup.nodes.Document

class TrackerItemDocumentAdapter : DocumentAdapter<List<TrackerItem>> {

    override fun convert(document: Document): List<TrackerItem> {
        val body = document.body()
        val topics = body
                .select("tbody > tr")

        return topics.map {
            val url = it
                    .select("td:eq(1)")
                    .select("a")
                    .first()
                    .attr("href")
                    .substring(1)
            val title = it
                    .select("td:eq(1)")
                    .select("a")
                    .first()
                    .ownText()
                    .parseAsHtml()
                    .toString()
            val groupTitle = it
                    .select("a.secondary")
                    .first()
                    .ownText()
            val tags = it
                    .select("span.tag")
                    .let(StringUtils::tagsFromElements)
            val date = it
                    .select("time")
                    .first()
                    .ownText()
            val author = it
                    .select("td.dateinterval > time")
                    .first()
                    .nextSibling()
                    .toString()
                    .replace(", ", "")
            val comments = it
                    .select("td.numbers")
                    .first()
                    .ownText()
                    .toString()
                    .toInt()

            TrackerItem(
                    url = url,
                    title = title,
                    groupTitle = groupTitle,
                    tags = tags,
                    date = date,
                    author = author,
                    comments = comments
            )
        }
    }
}
