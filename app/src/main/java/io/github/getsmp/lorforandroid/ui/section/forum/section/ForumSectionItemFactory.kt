/*
 * Copyright 2016 getsmp
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.getsmp.lorforandroid.ui.section.forum.section

import io.github.getsmp.lorforandroid.ui.section.Item
import io.github.getsmp.lorforandroid.ui.section.ItemFactory
import io.github.getsmp.lorforandroid.util.StringUtils
import org.jsoup.nodes.Element

class ForumSectionItemFactory : ItemFactory {
    override fun prepareItems(body: Element, items: MutableList<Any>) {
        val entries = body.select("tbody tr")
        for (entry in entries) {
            val properties = entry.select("td").first()
            val bareAuthor = properties.ownText()
            items.add(Item(
                    properties.select("a").first().attr("href").substring(1),
                    properties.select("a").first().ownText(),
                    null,
                    StringUtils.tagsFromElements(properties.select("a").first().select("span.tag")),
                    entry.select("td.dateinterval").first().select("time").first().ownText(),
                    bareAuthor.substring(bareAuthor.lastIndexOf("("), bareAuthor.lastIndexOf(")")).replace("[()]".toRegex(), ""),
                    StringUtils.numericStringToHumanReadable(entry.select("td.numbers").first().ownText())))
        }
    }
}
