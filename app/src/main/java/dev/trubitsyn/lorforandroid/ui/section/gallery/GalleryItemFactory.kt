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

package dev.trubitsyn.lorforandroid.ui.section.gallery

import android.text.Html
import dev.trubitsyn.lorforandroid.ui.section.ItemFactory
import dev.trubitsyn.lorforandroid.util.StringUtils
import org.jsoup.nodes.Element

class GalleryItemFactory : ItemFactory {
    override fun prepareItems(body: Element, items: MutableList<Any>) {
        val articles = body.select("article.news")
        for (article in articles) {
            val group = article.select("div.group").first()
            val imageUrl = article.select("a[itemprop^=contentURL]").attr("href")
            val withoutExtension = imageUrl.substring(0, imageUrl.length - 4)
            items.add(GalleryItem(
                    url = article.select("h2 > a[href^=/gallery/]").first().attr("href").substring(1),
                    title = Html.fromHtml(article.select("h2 > a[href^=/gallery/]").first().ownText()).toString(),
                    groupTitle = if (group != null) StringUtils.removeSectionName(group.text()) else null,
                    tags = StringUtils.tagsFromElements(article.select("a.tag")),
                    date = article.select("time").first().ownText().split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0],
                    author = article.select("a[itemprop^=creator], div.sign:contains(anonymous)").first().ownText().replace(" ()", ""),
                    comments = article.select("div.nav > a[href$=#comments]:eq(0)").first().ownText().replace("\\D+".toRegex(), ""),
                    imageUrl = imageUrl,
                    medium2xImageUrl = GalleryUtils.getMedium2xImageUrl(withoutExtension),
                    mediumImageUrl = GalleryUtils.getMediumImageUrl(withoutExtension)
            ))
        }
    }
}
