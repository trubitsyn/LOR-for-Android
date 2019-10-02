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

package dev.trubitsyn.lorforandroid.ui.section.tracker

import android.text.Html
import dev.trubitsyn.lorforandroid.ui.section.Item
import dev.trubitsyn.lorforandroid.ui.section.ItemFactory
import dev.trubitsyn.lorforandroid.util.StringUtils
import org.jsoup.nodes.Element

class TrackerItemFactory : ItemFactory {
    override fun prepareItems(body: Element, items: MutableList<Any>) {
        val topics = body.select("tbody > tr")
        for (topic in topics) {
            val url = topic
                    .select("td:eq(1)")
                    .select("a")
                    .first()
                    .attr("href")
                    .substring(1)
            val title = topic
                    .select("td:eq(1)")
                    .select("a")
                    .first()
                    .ownText()
                    .let { Html.fromHtml(it) }
                    .toString()
            val groupTitle = topic
                    .select("a.secondary")
                    .first()
                    .ownText()
            val tags = topic
                    .select("span.tag")
                    .let { StringUtils.tagsFromElements(it) }
            val date = topic
                    .select("time")
                    .first()
                    .ownText()
            val author = topic
                    .select("td.dateinterval > time")
                    .first()
                    .nextSibling()
                    .toString()
                    .replace(", ", "")
            val comments = topic
                    .select("td.numbers")
                    .first()
                    .ownText()
                    .let { StringUtils.numericStringToHumanReadable(it) }

            items.add(Item(
                    url = url,
                    title = title,
                    groupTitle = groupTitle,
                    tags = tags,
                    date = date,
                    author = author,
                    comments = comments
            ))
        }
    }
}
