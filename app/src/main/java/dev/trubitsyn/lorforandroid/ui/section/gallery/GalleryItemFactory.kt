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

package dev.trubitsyn.lorforandroid.ui.section.gallery

import androidx.core.text.parseAsHtml
import dev.trubitsyn.lorforandroid.ui.section.ItemFactory
import dev.trubitsyn.lorforandroid.util.StringUtils
import org.jsoup.nodes.Element

class GalleryItemFactory : ItemFactory {
    override fun prepareItems(body: Element, items: MutableList<Any>) {
        val articles = body
                .select("article.news")

        articles.forEach {
            val url = it
                    .select("h1 > a[href^=/gallery/]")
                    .first()
                    .attr("href")
                    .substring(1)
            val title = it
                    .select("h1 > a[href^=/gallery/]")
                    .first()
                    .ownText()
                    .parseAsHtml()
                    .toString()
            val group = it
                    .select("div.group")
                    .first()
            val groupTitle = group?.let { StringUtils.removeSectionName(group.text()) }
            val tags = StringUtils.tagsFromElements(it.select("a.tag"))
            val date = it
                    .select("time")
                    .first()
                    .ownText()
                    .split(" ".toRegex())
                    .dropLastWhile(String::isEmpty)
                    .toTypedArray()[0]
            val author = it
                    .select("a[itemprop^=creator], div.sign:contains(anonymous)")
                    .first()
                    .ownText()
                    .replace(" ()", "")
            val comments = it
                    .select("div.nav > a[href$=#comments]:eq(0)")
                    .first()
                    .ownText()
                    .replace("\\D+".toRegex(), "")
            val imageUrl = it
                    .select("a[itemprop^=contentURL]")
                    .attr("href")
            val withoutExtension = imageUrl.substring(0, imageUrl.length - 4)
            val medium2xImageUrl = GalleryUtils.getMedium2xImageUrl(withoutExtension)
            val mediumImageUrl = GalleryUtils.getMediumImageUrl(withoutExtension)

            items.add(GalleryItem(
                    url = url,
                    title = title,
                    groupTitle = groupTitle,
                    tags = tags,
                    date = date,
                    author = author,
                    comments = comments,
                    imageUrl = imageUrl,
                    medium2xImageUrl = medium2xImageUrl,
                    mediumImageUrl = mediumImageUrl
            ))
        }
    }
}
