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

package io.github.getsmp.lorforandroid.ui.section.forum;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import io.github.getsmp.lorforandroid.R;


public class ForumOverviewAdapter extends RecyclerView.Adapter<ForumOverviewViewHolder> {
    private List<ForumOverviewItem> sections;

    public ForumOverviewAdapter(List<ForumOverviewItem> sections) {
        this.sections = sections;
    }

    @Override
    public ForumOverviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_simple, parent, false);
        return new ForumOverviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ForumOverviewViewHolder v, int position) {
        v.itemName.setText(sections.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return sections.size();
    }
}
