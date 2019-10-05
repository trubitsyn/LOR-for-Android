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

package dev.trubitsyn.lorforandroid.ui.section.news

import android.text.Html
import dev.trubitsyn.lorforandroid.ui.section.Item
import dev.trubitsyn.lorforandroid.ui.section.ItemFactory
import dev.trubitsyn.lorforandroid.util.StringUtils
import org.jsoup.nodes.Element

class NewsItemFactory : ItemFactory {
    override fun prepareItems(body: Element, items: MutableList<Any>) {
        val articles = body
                .select("article")

        articles.forEach {
            if (it.hasClass("mini-news")) {
                // Mini-news article
                val url = it
                        .select("a[href^=/news/]")
                        .first()
                        .attr("href")
                        .substring(1)
                val title = it
                        .select("a[href^=/news/]")
                        .first()
                        .ownText()
                        .let(Html::fromHtml)
                        .toString()
                val commentsCount = it
                        .select("a")
                        .first()
                        .nextSibling()
                        .toString()
                        .let(Html::fromHtml)
                        .toString()
                        .replace("[()]".toRegex(), "")

                items.add(MiniNewsItem(
                        url = url,
                        title = title,
                        commentsCount = commentsCount
                ))
            } else {
                // Standard article
                val url = it
                        .select("h1 > a[href^=/news/]")
                        .first()
                        .attr("href")
                        .substring(1)
                val title = it
                        .select("h1 > a[href^=/news/]")
                        .first()
                        .ownText()
                        .let(Html::fromHtml)
                        .toString()
                val groupTitle = it
                        .select("div.group")
                        .first()
                        .text()
                        .let(StringUtils::removeSectionName)
                val tags = it
                        .select("a.tag")
                        .let(StringUtils::tagsFromElements)
                val date = it
                        .select("time")
                        .first()
                        .ownText()
                val author = it
                        .select("a[itemprop^=creator], div.sign:contains(anonymous)")
                        .first()
                        ?.ownText()
                        ?.replace(" ()", "")
                val comments = it
                        .select("div.nav > a[href$=#comments]:eq(0)")
                        .first()
                        ?.ownText()
                        ?: "Комментарии ограничены"

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
}
