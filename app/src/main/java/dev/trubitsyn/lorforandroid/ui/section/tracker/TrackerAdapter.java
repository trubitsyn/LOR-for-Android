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

package dev.trubitsyn.lorforandroid.ui.section.tracker;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import dev.trubitsyn.lorforandroid.R;
import dev.trubitsyn.lorforandroid.ui.section.Item;

class TrackerAdapter extends RecyclerView.Adapter<TrackerViewHolder> {
    private final List<Item> items;

    public TrackerAdapter(List<Item> items) {
        this.items = items;
    }

    @Override
    public TrackerViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.item_tracker, viewGroup, false);
        return new TrackerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrackerViewHolder viewHolder, int position) {
        Item item = items.get(position);
        viewHolder.title.setText(item.getTitle());
        viewHolder.category.setText(item.getGroupTitle());
        viewHolder.tags.setText(item.getTags());
        viewHolder.date.setText(item.getDate());
        viewHolder.author.setText(item.getAuthor());
        viewHolder.commentsCount.setText(item.getComments());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
