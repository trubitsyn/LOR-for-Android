/*
 * Copyright (c) 2016 getsmp
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

package io.github.getsmp.lorforandroid.ui.section.forum.section;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import io.github.getsmp.lorforandroid.R;
import io.github.getsmp.lorforandroid.ui.section.Item;

public class ForumSectionAdapter extends RecyclerView.Adapter<ForumSectionViewHolder> {
    private final List<Item> items;

    public ForumSectionAdapter(List<Item> items) {
        this.items = items;
    }

    @Override
    public ForumSectionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_forum, parent, false);
        return new ForumSectionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ForumSectionViewHolder v, int position) {
        Item item = items.get(position);
        v.title.setText(item.getTitle());
        if (item.getTags().length() > 0 || item.getTags() == null) {
            v.tags.setText(item.getTags());
        } else v.tags.setVisibility(View.GONE);
        v.replyFrom.setText(item.getAuthor());
        v.replyDate.setText(item.getDate());
        v.commentsCount.setText(item.getComments());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
