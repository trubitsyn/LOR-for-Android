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
import android.text.TextUtils;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import io.github.getsmp.lorforandroid.R;
import io.github.getsmp.lorforandroid.ui.base.BaseCallbackFragment;
import io.github.getsmp.lorforandroid.util.StringUtils;

public class GalleryFragment extends BaseCallbackFragment {
    private static final AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
    private final List<GalleryItem> items = new ArrayList<GalleryItem>();
    private int offset;

    @Override
    protected void getListItems() {
        if (offset <= 200) {
            startRefresh();

            RequestParams params = new RequestParams("offset", String.valueOf(offset));
            asyncHttpClient.get("https://www.linux.org.ru/gallery/", params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    String resp = null;
                    try {
                        resp = new String(responseBody, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        // Will never execute
                    }

                    Elements articles = Jsoup.parse(resp).body().select("article.news");

                    for (Element article : articles) {
                        items.add(new GalleryItem(
                                article.select("h2 > a[href^=/gallery/]").first().attr("href"),
                                article.select("h2 > a[href^=/gallery/]").first().ownText(),
                                article.select("div.group").first().text(),
                                article.select("time").first().ownText(),
                                article.select("div.nav > a[href$=#comments]:eq(0)").first().ownText(),
                                StringUtils.getTags(article.select("a.tag")),
                                article.select("a[itemprop^=creator], div.sign:contains(anonymous)").first().ownText().replace(" ()", ""),
                                article.select("img[itemprop^=thumbnail]").attr("src")
                        ));
                    }

                    offset += 20;
                    adapter.notifyDataSetChanged();
                    stopRefresh();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    networkError();
                }
            });
        } else Toast.makeText(context, R.string.error_no_more_data, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void clearData() {
        offset = 0;
        items.clear();
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        return new GalleryAdapter(items, context);
    }

    @Override
    protected String getUrl(int position) {
        return items.get(position).getUrl();
    }
}
