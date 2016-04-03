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

package io.github.getsmp.lorforandroid.ui.section.tracker;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import io.github.getsmp.lorforandroid.ui.section.ItemCommon;
import io.github.getsmp.lorforandroid.ui.section.ItemFactory;
import io.github.getsmp.lorforandroid.util.StringUtils;

public class TrackerItemFactory implements ItemFactory {
    @Override
    public List prepareItems(Element body) {
        List items = new ArrayList();
        Elements topics = body.select("tbody > tr");
        for (Element topic : topics) {
            items.add(new ItemCommon(
                    topic.select("td:eq(1)").select("a").first().attr("href").substring(1),
                    topic.select("td:eq(1)").select("a").first().ownText(),
                    topic.select("a.secondary").first().ownText(),
                    StringUtils.tagsFromElements(topic.select("span.tag")),
                    topic.select("time").first().ownText(),
                    topic.select("td.dateinterval > time").first().nextSibling().toString().replace(", ", ""),
                    StringUtils.addEnding(topic.select("td.numbers").first().ownText())
            ));
        }
        return items;
    }
}
