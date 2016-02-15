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

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.loopj.android.http.RequestParams;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import io.github.getsmp.lorforandroid.ui.section.ItemCommon;
import io.github.getsmp.lorforandroid.ui.section.SectionCommon;
import io.github.getsmp.lorforandroid.util.StringUtils;

public class ForumSectionFragment extends SectionCommon {
    private final List<ItemCommon> items = new ArrayList<ItemCommon>();
    private String group;

    public static ForumSectionFragment newInstance(String group) {
        ForumSectionFragment forumSectionFragment = new ForumSectionFragment();
        Bundle args = new Bundle();
        args.putString("group", group);
        forumSectionFragment.setArguments(args);
        return forumSectionFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        group = getArguments().getString("group");
    }

    @Override
    protected void clearData() {
        offset = 0;
        items.clear();
    }

    @Override
    protected List getDataSet() {
        return items;
    }

    @Override
    public int getItemsPerPage() {
        return 30;
    }

    @Override
    public int getMaxOffset() {
        return 300;
    }

    @Override
    public String getPath() {
        return "forum/" + group;
    }

    @Override
    public RequestParams getRequestParams() {
        return new RequestParams("offset", offset);
    }

    @Override
    protected void generateDataSet(Element responseBody) {
        Elements entries = responseBody.select("tbody tr");
        for (Element entry : entries) {
            Element properties = entry.select("td").first();
            String bareAuthor = properties.ownText();
            items.add(new ItemCommon(
                    properties.select("a").first().attr("href"),
                    properties.select("a").first().ownText(),
                    null,
                    StringUtils.tagsFromElements(properties.select("a").first().select("span.tag")),
                    entry.select("td.dateinterval").first().select("time").first().ownText(),
                    bareAuthor.substring(bareAuthor.lastIndexOf("("), bareAuthor.lastIndexOf(")")).replaceAll("[()]", ""),
                    StringUtils.addEnding(entry.select("td.numbers").first().ownText())
            ));
        }
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        return new ForumSectionAdapter(items);
    }

    @Override
    protected void onItemClickCallback(int position) {
        ((Callback) context).returnToActivity(items.get(position).getUrl());
    }

    interface Callback {
        void returnToActivity(String url);
    }
}
