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

package io.github.getsmp.lorforandroid.ui.section.tracker;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import io.github.getsmp.lorforandroid.R;

class TrackerAdapter extends RecyclerView.Adapter<TrackerViewHolder> {
    private final List<TrackerItem> items;

    public TrackerAdapter(List<TrackerItem> items) {
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
        TrackerItem item = items.get(position);
        viewHolder.title.setText(Html.fromHtml(item.getTitle()));
        viewHolder.category.setText(item.getGroupTitle());
        viewHolder.tags.setText(item.getTags());
        viewHolder.author.setText(item.getAuthor());
        viewHolder.date.setText(item.getPostDate());
        viewHolder.commentsCount.setText(item.getCommentsCount());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
