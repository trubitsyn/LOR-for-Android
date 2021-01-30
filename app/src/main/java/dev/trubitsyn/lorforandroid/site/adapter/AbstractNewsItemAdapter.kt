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
import dev.trubitsyn.lorforandroid.ui.section.news.AbstractNewsItem
import dev.trubitsyn.lorforandroid.ui.section.news.MiniNewsItem
import dev.trubitsyn.lorforandroid.ui.section.news.NewsItem
import dev.trubitsyn.lorforandroid.util.StringUtils
import org.jsoup.nodes.Document

class AbstractNewsItemAdapter : DocumentAdapter<List<AbstractNewsItem>> {
    override fun convert(document: Document): List<AbstractNewsItem> {
        val body = document.body()
        val items = mutableListOf<AbstractNewsItem>()
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
                        .parseAsHtml()
                        .toString()
                val commentsCount = it
                        .select("a")
                        .first()
                        .nextSibling()
                        .toString()
                        .parseAsHtml()
                        .toString()
                        .replace("[()]".toRegex(), "")
                        .replace("[^0-9.]".toRegex(), "")
                        .toInt()

                items.add(MiniNewsItem(
                        url = url,
                        title = title,
                        comments = commentsCount
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
                        .parseAsHtml()
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
                        .toString()
                        .replace("[^0-9.]".toRegex(), "")
                        .toInt()

                items.add(NewsItem(
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
        return items
    }
}