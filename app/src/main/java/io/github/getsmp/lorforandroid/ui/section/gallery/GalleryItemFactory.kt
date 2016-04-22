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

package io.github.getsmp.lorforandroid.ui.section.gallery

import android.text.Html
import io.github.getsmp.lorforandroid.ui.section.ItemFactory
import io.github.getsmp.lorforandroid.util.StringUtils
import org.jsoup.nodes.Element

class GalleryItemFactory : ItemFactory {
    override fun prepareItems(body: Element, items: MutableList<Any>) {
        val articles = body.select("article.news")
        for (article in articles) {
            val imageUrl = article.select("a[itemprop^=contentURL]").attr("href")
            val withoutExtension = imageUrl.substring(0, imageUrl.length - 4)
            items.add(GalleryItem(
                    url = article.select("h2 > a[href^=/gallery/]").first().attr("href").substring(1),
                    title = Html.fromHtml(article.select("h2 > a[href^=/gallery/]").first().ownText()).toString(),
                    groupTitle = StringUtils.removeSectionName(article.select("div.group").first()?.text()) ?: null,
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
