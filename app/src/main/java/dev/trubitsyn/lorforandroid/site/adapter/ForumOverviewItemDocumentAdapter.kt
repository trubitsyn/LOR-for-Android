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

import dev.trubitsyn.lorforandroid.site.DocumentAdapter
import dev.trubitsyn.lorforandroid.ui.section.forum.ForumOverviewItem
import org.jsoup.nodes.Document

class ForumOverviewItemDocumentAdapter : DocumentAdapter<List<ForumOverviewItem>> {

    override fun convert(document: Document): List<ForumOverviewItem> {
        val items = mutableListOf<ForumOverviewItem>()
        val body = document.body()
        val sections = body
                .select("div#bd")
                .select("ul")
                .first()
                .select("li")

        sections.forEach {
            val url = it
                    .select("a")
                    .first()
                    .attr("href")
                    .replace("/forum/", "")
                    .replace("/", "")
            val name = it
                    .select("a")
                    .first()
                    .ownText()

            items.add(ForumOverviewItem(
                    url = url,
                    name = name
            ))
        }
        return items
    }
}
