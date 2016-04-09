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

package io.github.getsmp.lorforandroid.ui.section.forum.section;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;

import io.github.getsmp.lorforandroid.ui.section.ItemCommon;
import io.github.getsmp.lorforandroid.ui.section.ItemFactory;
import io.github.getsmp.lorforandroid.util.StringUtils;

public class ForumSectionItemFactory implements ItemFactory {
    @Override
    public void prepareItems(Element body, List items) {
        Elements entries = body.select("tbody tr");
        for (Element entry : entries) {
            Element properties = entry.select("td").first();
            String bareAuthor = properties.ownText();
            items.add(new ItemCommon(
                    properties.select("a").first().attr("href").substring(1),
                    properties.select("a").first().ownText(),
                    null,
                    StringUtils.tagsFromElements(properties.select("a").first().select("span.tag")),
                    entry.select("td.dateinterval").first().select("time").first().ownText(),
                    bareAuthor.substring(bareAuthor.lastIndexOf("("), bareAuthor.lastIndexOf(")")).replaceAll("[()]", ""),
                    StringUtils.addEnding(entry.select("td.numbers").first().ownText())
            ));
        }
    }
}
