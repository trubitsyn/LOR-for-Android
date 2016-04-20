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

package io.github.getsmp.lorforandroid.ui.section.gallery;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import io.github.getsmp.lorforandroid.R;
import io.github.getsmp.lorforandroid.util.PreferenceUtils;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryViewHolder> {
    private final List<GalleryItem> items;
    private final Context context;
    private final boolean shouldLoadImages;

    public GalleryAdapter(List<GalleryItem> items, Context context) {
        this.items = items;
        this.context = context;
        shouldLoadImages = PreferenceUtils.shouldLoadImagesNow(context);
    }

    @Override
    public GalleryViewHolder onCreateViewHolder(final ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_gallery, viewGroup, false);
        return new GalleryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GalleryViewHolder viewHolder, int i) {
        GalleryItem item = items.get(i);
        viewHolder.title.setText(item.getTitle());

        if (item.getGroupTitle() == null) {
            viewHolder.category.setVisibility(View.GONE);
        } else viewHolder.category.setText(item.getGroupTitle());

        if (item.getTags().isEmpty()) {
            viewHolder.tags.setVisibility(View.GONE);
        } else viewHolder.tags.setText(item.getTags());

        viewHolder.date.setText(item.getDate());
        viewHolder.author.setText(item.getAuthor());
        viewHolder.commentsCount.setText(item.getComments());

        if (shouldLoadImages) {
            Glide.with(context).load(item.getMediumImageUrl()).diskCacheStrategy(DiskCacheStrategy.ALL).into(viewHolder.image);
        } else viewHolder.image.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}