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

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.loopj.android.http.RequestParams;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import io.github.getsmp.lorforandroid.R;
import io.github.getsmp.lorforandroid.ui.section.SectionCommon;
import io.github.getsmp.lorforandroid.ui.util.FragmentReplaceCallback;
import io.github.getsmp.lorforandroid.ui.util.ItemClickCallback;
import io.github.getsmp.lorforandroid.ui.util.SpinnerViewUtils;
import io.github.getsmp.lorforandroid.util.StringUtils;

public class GalleryFragment extends SectionCommon {
    private Spinner spinner;
    private String filter;

    public static GalleryFragment newInstance(GalleryFilterEnum galleryFilterEnum) {
        GalleryFragment galleryFragment = new GalleryFragment();
        Bundle args = new Bundle();
        args.putString("filter", galleryFilterEnum.name());
        galleryFragment.setArguments(args);
        return galleryFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        filter = getArguments().getString("filter");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SpinnerViewUtils.setSpinnerView(getActivity(), R.array.gallery_spinner, GalleryFilterEnum.valueOf(filter).ordinal(), new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((FragmentReplaceCallback) getActivity()).replace(R.id.fragmentContainer, newInstance(GalleryFilterEnum.values()[position]), "gallery");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    @Override
    protected void generateDataSet(Element responseBody) {
        Elements articles = responseBody.select("article.news");
        for (Element article : articles) {
            items.add(new GalleryItem(
                    article.select("h2 > a[href^=/gallery/]").first().attr("href").substring(1),
                    article.select("h2 > a[href^=/gallery/]").first().ownText(),
                    isAll() ? StringUtils.removeSectionName(article.select("div.group").first().text()) : null,
                    StringUtils.tagsFromElements(article.select("a.tag")),
                    article.select("time").first().ownText().split(" ")[0],
                    article.select("a[itemprop^=creator], div.sign:contains(anonymous)").first().ownText().replace(" ()", ""),
                    article.select("div.nav > a[href$=#comments]:eq(0)").first().ownText().replaceAll("\\D+", ""),
                    article.select("img[itemprop^=thumbnail]").attr("src")
            ));
        }
    }

    private boolean isAll() {
        return filter.equals(GalleryFilterEnum.all.name());
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
        String path = isAll() ? "" : "/" + filter;
        return "gallery" + path;
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
        GalleryItem item = (GalleryItem) items.get(position);
        ((ItemClickCallback) context).onGalleryTopicRequested(item);
    }
}
