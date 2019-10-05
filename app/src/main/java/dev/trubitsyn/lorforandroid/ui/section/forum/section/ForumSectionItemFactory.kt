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

package dev.trubitsyn.lorforandroid.ui.section.forum.section

import dev.trubitsyn.lorforandroid.ui.section.ItemFactory
import dev.trubitsyn.lorforandroid.util.StringUtils
import org.jsoup.nodes.Element

class ForumSectionItemFactory : ItemFactory {
    override fun prepareItems(body: Element, items: MutableList<Any>) {
        val entries = body
                .select("tbody tr")

        entries.forEach {
            val properties = it
                    .select("td")
                    .first()
            val url = properties
                    .select("a")
                    .first()
                    .attr("href")
                    .substring(1)
            val title = properties
                    .select("a")
                    .first()
                    .ownText()
            val tags = properties
                    .select("a")
                    .first()
                    .select("span.tag")
                    .let(StringUtils::tagsFromElements)
            val date = it
                    .select("td.dateinterval")
                    .first()
                    .select("time")
                    .first()
                    .ownText()
            val bareAuthor = properties
                    .ownText()
            val author = bareAuthor
                    .substring(bareAuthor.lastIndexOf("("), bareAuthor.lastIndexOf(")"))
                    .replace("[()]".toRegex(), "")
            val comments = it
                    .select("td.numbers")
                    .first()
                    .ownText()
                    .let(StringUtils::numericStringToHumanReadable)
            val isPinned = properties
                    .select("i.icon-pin")
                    .size > 0
            items.add(ForumSectionItem(
                    url = url,
                    title = title,
                    groupTitle = null,
                    tags = tags,
                    date = date,
                    author = author,
                    comments = comments,
                    isPinned = isPinned
            ))
        }
    }
}
