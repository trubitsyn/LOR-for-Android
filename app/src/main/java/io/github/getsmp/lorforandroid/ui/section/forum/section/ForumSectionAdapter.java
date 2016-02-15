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

package io.github.getsmp.lorforandroid.ui.section.forum.section;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import io.github.getsmp.lorforandroid.R;
import io.github.getsmp.lorforandroid.ui.section.ItemCommon;

public class ForumSectionAdapter extends RecyclerView.Adapter<ForumSectionViewHolder> {
    private List<ItemCommon> items;

    public ForumSectionAdapter(List<ItemCommon> items) {
        this.items = items;
    }

    @Override
    public ForumSectionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_forum, parent, false);
        return new ForumSectionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ForumSectionViewHolder v, int position) {
        ItemCommon item = items.get(position);
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
