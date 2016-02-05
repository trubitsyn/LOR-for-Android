/*
 * Copyright 2015 getsmp
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

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;

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
import io.github.getsmp.lorforandroid.ui.base.BaseCallbackFragment;

public class NewsFragment extends BaseCallbackFragment {
    protected final List<NewsItem> items = new ArrayList<NewsItem>();
    private static final int itemsPerPage = 20;
    private int offset = 0;
    private static final AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

    @Override
    public void getListItems() {
        startRefresh();

        RequestParams params = new RequestParams("offset", String.valueOf(offset));
        asyncHttpClient.get("https://www.linux.org.ru/news/", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String resp = null;
                try {
                    resp = new String(responseBody, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    // Will never execute
                }

                Elements articles = Jsoup.parse(resp).body().select("article");

                List<NewsItem> newsItems = new ArrayList<NewsItem>();
                for (Element article : articles) {
                    if (article.hasClass("mini-news")) {
                        // Mini-news article
                        newsItems.add(new NewsItem(
                                article.select("a[href^=/news/]").first().attr("href"),
                                article.select("a[href^=/news/]").first().ownText(),
                                null,
                                null,
                                Html.fromHtml(article.select("a").first().nextSibling().toString()).toString().replaceAll("[()]", ""),
                                null,
                                null,
                                true
                        ));
                    } else {
                        // Standard article
                        Elements tags = article.select("a.tag");
                        List<String> strTags = new ArrayList<String>();
                        for (Element tag : tags) {
                            strTags.add(tag.ownText());
                        }

                        newsItems.add(new NewsItem(
                                article.select("h2 > a[href^=/news/]").first().attr("href"),
                                article.select("h2 > a[href^=/news/]").first().ownText(),
                                article.select("div.group").first().text(),
                                article.select("time").first().ownText(),
                                article.select("div.nav > a[href$=#comments]:eq(0)").first().ownText(),
                                TextUtils.join(", ", strTags),
                                article.select("a[itemprop^=creator], div.sign:contains(anonymous)").first().ownText().replace(" ()", ""),
                                false
                        ));
                    }
                }

                offset += itemsPerPage;
                items.addAll(newsItems);
                adapter.notifyDataSetChanged();
                stopRefresh();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                networkError();
            }
        });
    }

    @Override
    protected void clearData() {
        offset = 0;
        items.clear();
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        return new NewsAdapter(items);
    }

    @Override
    protected String getUrl(int position) {
        return items.get(position).getUrl();
    }
}
