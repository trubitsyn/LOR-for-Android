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

package io.github.getsmp.lorforandroid.ui.section.forum;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import io.github.getsmp.lorforandroid.ui.section.ItemFactory;

public class ForumOverviewItemFactory implements ItemFactory {
    @Override
    public List prepareItems(Element body) {
        List<ForumOverviewItem> items = new ArrayList<ForumOverviewItem>();
        Elements sections = body.select("div#bd").select("ul").first().select("li");
        for (Element section : sections) {
            items.add(new ForumOverviewItem(
                    section.select("a").first().attr("href").replace("/forum/", "").replace("/", ""),
                    section.select("a").first().ownText()
            ));
        }
        return items;
    }
}
