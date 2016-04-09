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

package io.github.getsmp.lorforandroid.ui.section.gallery;

import android.text.Html;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;

import io.github.getsmp.lorforandroid.ui.section.ItemFactory;
import io.github.getsmp.lorforandroid.util.StringUtils;

public class GalleryItemFactory implements ItemFactory {
    @Override
    public void prepareItems(Element body, List items) {
        Elements articles = body.select("article.news");
        for (Element article : articles) {
            Element group = article.select("div.group").first();
            items.add(new GalleryItem(
                    article.select("h2 > a[href^=/gallery/]").first().attr("href").substring(1),
                    Html.fromHtml(article.select("h2 > a[href^=/gallery/]").first().ownText()).toString(),
                    (group != null) ? StringUtils.removeSectionName(group.text()) : null,
                    StringUtils.tagsFromElements(article.select("a.tag")),
                    article.select("time").first().ownText().split(" ")[0],
                    article.select("a[itemprop^=creator], div.sign:contains(anonymous)").first().ownText().replace(" ()", ""),
                    article.select("div.nav > a[href$=#comments]:eq(0)").first().ownText().replaceAll("\\D+", ""),
                    article.select("img[itemprop^=thumbnail]").attr("src")
            ));
        }
    }
}
