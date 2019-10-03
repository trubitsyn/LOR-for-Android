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

package dev.trubitsyn.lorforandroid.ui.section.gallery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import dev.trubitsyn.lorforandroid.R;
import dev.trubitsyn.lorforandroid.util.PreferenceUtils;

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