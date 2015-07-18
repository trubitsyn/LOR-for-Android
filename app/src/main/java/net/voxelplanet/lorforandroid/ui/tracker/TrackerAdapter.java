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

package net.voxelplanet.lorforandroid.ui.tracker;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import net.voxelplanet.lorforandroid.R;
import net.voxelplanet.lorforandroid.model.TrackerItem;
import net.voxelplanet.lorforandroid.ui.gallery.GalleryViewHolder;
import net.voxelplanet.lorforandroid.ui.news.NewsViewHolder;
import net.voxelplanet.lorforandroid.util.StringUtils;

import java.util.List;

class TrackerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<TrackerItem> trackerItems;
    private final Activity activity;
    private final int NEWS = 0, GALLERY = 1;

    public TrackerAdapter(List<TrackerItem> trackerItems, Activity activity) {
        this.trackerItems = trackerItems;
        this.activity = activity;
    }

    @Override
    public int getItemViewType(int position) {
        // TODO: It would be better to check this with API method
        if (trackerItems.get(position).getUrl().contains("gallery")) {
            return GALLERY;
        } else return NEWS;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());

        switch (viewType) {
            case NEWS:
                View news = layoutInflater.inflate(R.layout.item_news, viewGroup, false);
                viewHolder = new NewsViewHolder(news);
                break;
            case GALLERY:
                View gallery = layoutInflater.inflate(R.layout.item_gallery, viewGroup, false);
                viewHolder = new GalleryViewHolder(gallery);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        TrackerItem trackerItem = trackerItems.get(position);
        switch (viewHolder.getItemViewType()) {
            // TODO: This method uses duplicates NewsAdapter's and GalleryAdapter's methods
            case NEWS:
                NewsViewHolder newsViewHolder = (NewsViewHolder) viewHolder;
                newsViewHolder.getTitle().setText(Html.fromHtml(trackerItem.getTitle()));
                newsViewHolder.getCategory().setText(trackerItem.getGroupTitle());
                newsViewHolder.getAuthor().setText(trackerItem.getLastCommentedBy());
                newsViewHolder.getDate().setText(StringUtils.getDate(trackerItem.getPostDate()));

                if (trackerItem.getTags().size() == 0) {
                    newsViewHolder.getTags().setVisibility(View.GONE);
                } else newsViewHolder.getTags().setText(TextUtils.join(", ", trackerItem.getTags()));

                newsViewHolder.getCommentsCount().setVisibility(View.GONE);
                break;
            case GALLERY:
                GalleryViewHolder galleryViewHolder = (GalleryViewHolder) viewHolder;
                galleryViewHolder.getTitle().setText(Html.fromHtml(trackerItem.getTitle()) + " (" + trackerItem.getAuthor() + ")");

                String url = StringUtils.clearUrl(trackerItem.getUrl());
                Picasso.with(activity).cancelRequest(galleryViewHolder.getImage());
                // TODO: Remove hardcoded URL
                String imageUrl = "https://linux.org.ru/gallery" + url.substring(url.lastIndexOf("/")) + "-med.jpg";
                Picasso.with(activity).load(imageUrl).resize(400, 0).into((galleryViewHolder.getImage()));

                if (trackerItem.getTags().size() == 0) {
                    galleryViewHolder.getTags().setVisibility(View.GONE);
                } else galleryViewHolder.getTags().setText(TextUtils.join(", ", trackerItem.getTags()));

                //galleryViewHolder.getCommentsCount().setText(item.getCommentsCount());
        }
    }

    @Override
    public int getItemCount() {
        return trackerItems.size();
    }
}
