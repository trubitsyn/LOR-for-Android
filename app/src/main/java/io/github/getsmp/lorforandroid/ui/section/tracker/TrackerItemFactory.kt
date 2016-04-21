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

package io.github.getsmp.lorforandroid.ui.section.tracker

import android.text.Html
import io.github.getsmp.lorforandroid.ui.section.Item
import io.github.getsmp.lorforandroid.ui.section.ItemFactory
import io.github.getsmp.lorforandroid.util.StringUtils
import org.jsoup.nodes.Element

class TrackerItemFactory : ItemFactory {
    override fun prepareItems(body: Element, items: MutableList<Any>) {
        val topics = body.select("tbody > tr")
        for (topic in topics) {
            items.add(Item(
                    url = topic.select("td:eq(1)").select("a").first().attr("href").substring(1),
                    title = Html.fromHtml(topic.select("td:eq(1)").select("a").first().ownText()).toString(),
                    groupTitle = topic.select("a.secondary").first().ownText(),
                    tags = StringUtils.tagsFromElements(topic.select("span.tag")),
                    date = topic.select("time").first().ownText(),
                    author = topic.select("td.dateinterval > time").first().nextSibling().toString().replace(", ", ""),
                    comments = StringUtils.numericStringToHumanReadable(topic.select("td.numbers").first().ownText())))
        }
    }
}
