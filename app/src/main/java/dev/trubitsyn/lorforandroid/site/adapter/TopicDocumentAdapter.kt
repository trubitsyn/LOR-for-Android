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
import dev.trubitsyn.lorforandroid.ui.topic.TopicItem
import org.jsoup.nodes.Document

class TopicDocumentAdapter : DocumentAdapter<TopicItem> {
    override fun convert(document: Document): TopicItem {
        val body = document.body()
        val title = body.select("header > h1")
                .text()
        val url = body.select("header > h1 > a")
                .attr("href")
        val tags = "tag1, tag2"
        val message = body.select("div[itemprop=articleBody] > p")
                .text()
                .parseAsHtml()
                .toString()
        val postDate = body.select("footer > sign > time")
                .text()
        val sticky = false
        val commentsCount = 0
        val favsCount = 0
        val watchCount = 0
        val postScore = 0
        val author = "author"

        return TopicItem(
                url = url,
                title = title,
                tags = tags,
                message = message,
                postDate = postDate,
                sticky = sticky,
                commentsCount = commentsCount,
                favsCount = favsCount,
                watchCount = watchCount,
                postScore = postScore,
                author = author
        )
    }
}