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

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<Object> items;
    private final int MINI = 0, FULL = 1;

    public NewsAdapter(List<Object> topics) {
        this.items = topics;
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position) instanceof MiniNewsItem) {
            return MINI;
        } else if (items.get(position) instanceof NewsItem) {
            return FULL;
        }
        return -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;

        switch (viewType) {
            case MINI:
                View mini = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_mini_news, viewGroup, false);
                viewHolder = new MiniNewsViewHolder(mini);
                break;
            case FULL:
                View full = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_news, viewGroup, false);
                viewHolder = new NewsViewHolder(full);
                break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        switch (viewHolder.getItemViewType()) {
            case MINI:
                MiniNewsViewHolder miniNewsViewHolder = (MiniNewsViewHolder) viewHolder;
                MiniNewsItem miniNewsItem = (MiniNewsItem) items.get(i);
                miniNewsViewHolder.getTitle().setText(Html.fromHtml(miniNewsItem.getTitle()));
                miniNewsViewHolder.getCommentsCount().setText(miniNewsItem.getCommentsCount());
                break;
            case FULL:
                NewsViewHolder newsViewHolder = (NewsViewHolder) viewHolder;
                NewsItem newsItem = (NewsItem) items.get(i);
                newsViewHolder.getTitle().setText(Html.fromHtml(newsItem.getTitle()));
                newsViewHolder.getCategory().setText(newsItem.getGroupTitle());
                newsViewHolder.getTags().setText(newsItem.getTags());
                newsViewHolder.getAuthor().setText(newsItem.getAuthor());
                newsViewHolder.getDate().setText(newsItem.getPostDate());
                newsViewHolder.getCommentsCount().setText(newsItem.getCommentsCount());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
