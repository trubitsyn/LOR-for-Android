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

import android.support.v7.widget.RecyclerView;

import com.loopj.android.http.RequestParams;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import io.github.getsmp.lorforandroid.ui.section.SectionCommon;
import io.github.getsmp.lorforandroid.ui.util.ItemClickCallback;
import io.github.getsmp.lorforandroid.util.StringUtils;

public class GalleryFragment extends SectionCommon {
    private List<GalleryItem> items = new ArrayList<GalleryItem>();

    @Override
    protected void generateDataSet(Element responseBody) {
        Elements articles = responseBody.select("article.news");
        for (Element article : articles) {
            items.add(new GalleryItem(
                    article.select("h2 > a[href^=/gallery/]").first().attr("href"),
                    article.select("h2 > a[href^=/gallery/]").first().ownText(),
                    StringUtils.removeSectionName(article.select("div.group").first().text()),
                    article.select("time").first().ownText(),
                    article.select("div.nav > a[href$=#comments]:eq(0)").first().ownText(),
                    StringUtils.tagsFromElements(article.select("a.tag")),
                    article.select("a[itemprop^=creator], div.sign:contains(anonymous)").first().ownText().replace(" ()", ""),
                    article.select("img[itemprop^=thumbnail]").attr("src")
            ));
        }
    }

    @Override
    protected List getDataSet() {
        return items;
    }

    @Override
    public int getItemsPerPage() {
        return 20;
    }

    @Override
    public int getMaxOffset() {
        return 200;
    }

    @Override
    public String getPath() {
        return "gallery";
    }

    @Override
    public RequestParams getRequestParams() {
        return new RequestParams("offset", offset);
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        return new GalleryAdapter(items, context);
    }

    @Override
    protected void onItemClickCallback(int position) {
        ((ItemClickCallback) context).onTopicRequested(items.get(position).getUrl());
    }
}
