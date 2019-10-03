/*
 * Copyright (C) 2015-2016 Nikola Trubitsyn
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package dev.trubitsyn.lorforandroid.ui.section.news;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dev.trubitsyn.lorforandroid.R;
import dev.trubitsyn.lorforandroid.ui.section.Item;

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<Object> items;
    private static final int MINI = 0, FULL = 1;

    public NewsAdapter(List<Object> topics) {
        this.items = topics;
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position) instanceof MiniNewsItem) {
            return MINI;
        } else if (items.get(position) instanceof Item) {
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
                Item newsItem = (Item) items.get(i);
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
