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
        val articles = body.select("article")
        for (article in articles) {
            if (article.hasClass("mini-news")) {
                // Mini-news article
                items.add(MiniNewsItem(
                        url = article.select("a[href^=/news/]").first().attr("href").substring(1),
                        title = Html.fromHtml(article.select("a[href^=/news/]").first().ownText()).toString(),
                        commentsCount = Html.fromHtml(article.select("a").first().nextSibling().toString()).toString().replace("[()]".toRegex(), "")
                ))
            } else {
                // Standard article
                items.add(Item(
                        url = article.select("h2 > a[href^=/news/]").first().attr("href").substring(1),
                        title = Html.fromHtml(article.select("h2 > a[href^=/news/]").first().ownText()).toString(),
                        groupTitle = StringUtils.removeSectionName(article.select("div.group").first().text()),
                        tags = StringUtils.tagsFromElements(article.select("a.tag")),
                        date = article.select("time").first().ownText(),
                        author = article.select("a[itemprop^=creator], div.sign:contains(anonymous)").first()?.ownText()?.replace(" ()", ""),
                        comments = article.select("div.nav > a[href$=#comments]:eq(0)").first()?.ownText() ?: "Комментарии ограничены"
                ))
            }
        }
    }
}
