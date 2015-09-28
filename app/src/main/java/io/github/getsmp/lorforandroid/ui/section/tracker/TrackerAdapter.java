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

package io.github.getsmp.lorforandroid.ui.section.tracker;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.github.getsmp.lorforandroid.R;
import io.github.getsmp.lorforandroid.model.TrackerItem;
import io.github.getsmp.lorforandroid.ui.section.gallery.GalleryAdapter;
import io.github.getsmp.lorforandroid.ui.section.gallery.GalleryViewHolder;
import io.github.getsmp.lorforandroid.ui.section.news.NewsAdapter;
import io.github.getsmp.lorforandroid.ui.section.news.NewsViewHolder;
import io.github.getsmp.lorforandroid.util.StringUtils;

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
        if (StringUtils.isGallery(trackerItems.get(position).getUrl())) {
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
        TrackerItem item = trackerItems.get(position);
        switch (viewHolder.getItemViewType()) {
            case NEWS:
                NewsViewHolder newsViewHolder = (NewsViewHolder) viewHolder;
                NewsAdapter.initView(newsViewHolder, item.getTitle(), item.getGroupTitle(), item.getTags(), item.getLastCommentedBy(), item.getPostDate(), 0);
                break;
            case GALLERY:
                GalleryViewHolder galleryViewHolder = (GalleryViewHolder) viewHolder;
                GalleryAdapter.initView(galleryViewHolder, activity, item.getTitle(), item.getGroupTitle(), StringUtils.getImageUrl(item.getUrl(), "med"), item.getTags(), item.getAuthor(), item.getPostDate(), 0);
        }
    }

    @Override
    public int getItemCount() {
        return trackerItems.size();
    }
}
