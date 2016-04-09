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

package io.github.getsmp.lorforandroid.ui.section.news;

import android.text.Html;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;

import io.github.getsmp.lorforandroid.ui.section.ItemCommon;
import io.github.getsmp.lorforandroid.ui.section.ItemFactory;
import io.github.getsmp.lorforandroid.util.StringUtils;

public class NewsItemFactory implements ItemFactory {
    @Override
    public void prepareItems(Element body, List items) {
        Elements articles = body.select("article");
        for (Element article : articles) {
            if (article.hasClass("mini-news")) {
                // Mini-news article
                items.add(new MiniNewsItem(
                        article.select("a[href^=/news/]").first().attr("href").substring(1),
                        Html.fromHtml(article.select("a[href^=/news/]").first().ownText()).toString(),
                        Html.fromHtml(article.select("a").first().nextSibling().toString()).toString().replaceAll("[()]", "")
                ));
            } else {
                // Standard article
                items.add(new ItemCommon(
                        article.select("h2 > a[href^=/news/]").first().attr("href").substring(1),
                        Html.fromHtml(article.select("h2 > a[href^=/news/]").first().ownText()).toString(),
                        StringUtils.removeSectionName(article.select("div.group").first().text()),
                        StringUtils.tagsFromElements(article.select("a.tag")),
                        article.select("time").first().ownText(),
                        article.select("a[itemprop^=creator], div.sign:contains(anonymous)").first().ownText().replace(" ()", ""),
                        article.select("div.nav > a[href$=#comments]:eq(0)").first().ownText()
                ));
            }
        }
    }
}
