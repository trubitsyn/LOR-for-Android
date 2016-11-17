/*
 * Copyright (c) 2016 getsmp
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

package io.github.getsmp.lorforandroid.ui.section.forum.section

import io.github.getsmp.lorforandroid.ui.section.ItemFactory
import io.github.getsmp.lorforandroid.util.StringUtils
import org.jsoup.nodes.Element

class ForumSectionItemFactory : ItemFactory {
    override fun prepareItems(body: Element, items: MutableList<Any>) {
        val entries = body.select("tbody tr")
        for (entry in entries) {
            val properties = entry.select("td").first()
            val bareAuthor = properties.ownText()
            items.add(ForumSectionItem(
                    url = properties.select("a").first().attr("href").substring(1),
                    title = properties.select("a").first().ownText(),
                    groupTitle = null,
                    tags = StringUtils.tagsFromElements(properties.select("a").first().select("span.tag")),
                    date = entry.select("td.dateinterval").first().select("time").first().ownText(),
                    author = bareAuthor.substring(bareAuthor.lastIndexOf("("), bareAuthor.lastIndexOf(")")).replace("[()]".toRegex(), ""),
                    comments = StringUtils.numericStringToHumanReadable(entry.select("td.numbers").first().ownText()),
                    isPinned = properties.select("i.icon-pin").size > 0
            ))
        }
    }
}
