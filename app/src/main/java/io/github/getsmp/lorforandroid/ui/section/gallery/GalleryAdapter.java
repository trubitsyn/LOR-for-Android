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

package io.github.getsmp.lorforandroid.ui.section.gallery;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import io.github.getsmp.lorforandroid.R;
import io.github.getsmp.lorforandroid.ui.section.news.NewsItem;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryViewHolder> {
    private final List<NewsItem> topics;
    private final Context context;

    public GalleryAdapter(List<NewsItem> topics, Context context) {
        this.topics = topics;
        this.context = context;
    }

    @Override
    public GalleryViewHolder onCreateViewHolder(final ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_gallery, viewGroup, false);
        return new GalleryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GalleryViewHolder viewHolder, int i) {
        /*NewsItem topic = topics.get(i);
        viewHolder.getTitle().setText(Html.fromHtml(topic.getTitle()) + " (" + nick + ")");
        viewHolder.getCategory().setText(groupTitle);

        if (imageUrl != null) {
            Picasso.with(context).cancelRequest(viewHolder.getImage());
            Picasso.with(context).load(imageUrl).resize(400, 0).into((viewHolder.getImage()));
        } else viewHolder.getImage().setVisibility(View.GONE);

        if (tags.length() == 0) {
            viewHolder.getTags().setVisibility(View.GONE);
        } else viewHolder.getTags().setText(tags);

        //viewHolder.getCommentsCount().setText(commentsCount);*/
    }

    @Override
    public int getItemCount() {
        return topics.size();
    }
}
