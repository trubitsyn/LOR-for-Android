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

package io.github.getsmp.lorforandroid.ui.section.news;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import io.github.getsmp.lorforandroid.R;
import io.github.getsmp.lorforandroid.ui.section.ItemCommon;

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
        } else if (items.get(position) instanceof ItemCommon) {
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
                miniNewsViewHolder.getTitle().setText(miniNewsItem.getTitle());
                miniNewsViewHolder.getCommentsCount().setText(miniNewsItem.getCommentsCount());
                break;
            case FULL:
                NewsViewHolder newsViewHolder = (NewsViewHolder) viewHolder;
                ItemCommon newsItem = (ItemCommon) items.get(i);
                newsViewHolder.title.setText(newsItem.getTitle());
                newsViewHolder.category.setText(newsItem.getGroupTitle());
                newsViewHolder.tags.setText(newsItem.getTags());
                newsViewHolder.author.setText(newsItem.getAuthor());
                newsViewHolder.date.setText(newsItem.getDate());
                newsViewHolder.commentsCount.setText(newsItem.getComments());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
