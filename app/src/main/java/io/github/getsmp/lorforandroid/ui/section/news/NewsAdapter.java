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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import io.github.getsmp.lorforandroid.R;

public class NewsAdapter extends RecyclerView.Adapter<NewsViewHolder> {
    private final List<NewsItem> newsItems;

    public NewsAdapter(List<NewsItem> topics) {
        this.newsItems = topics;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(final ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_news, viewGroup, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsViewHolder viewHolder, int i) {
        NewsItem topic = newsItems.get(i);
        if (topic.isMiniNews()) {
            viewHolder.getCategory().setVisibility(View.GONE);
            viewHolder.getTags().setVisibility(View.GONE);
            viewHolder.getAuthor().setVisibility(View.GONE);
            viewHolder.getDate().setVisibility(View.GONE);
        } else {
            viewHolder.getCategory().setText(topic.getGroupTitle());
            viewHolder.getTags().setText(topic.getTags());
            viewHolder.getAuthor().setText(topic.getAuthor());
            viewHolder.getDate().setText(topic.getPostDate());
        }
        viewHolder.getTitle().setText(Html.fromHtml(topic.getTitle()));
        viewHolder.getCommentsCount().setText(topic.getCommentsCount());
    }

    @Override
    public int getItemCount() {
        return newsItems.size();
    }
}
