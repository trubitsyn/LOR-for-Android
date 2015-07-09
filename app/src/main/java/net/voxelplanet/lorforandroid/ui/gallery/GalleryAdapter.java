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

package net.voxelplanet.lorforandroid.ui.gallery;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import net.voxelplanet.lorforandroid.R;
import net.voxelplanet.lorforandroid.model.Topic;
import net.voxelplanet.lorforandroid.util.StringUtils;

import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryViewHolder> {
    private final List<Topic> topics;
    private final Activity activity;

    public GalleryAdapter(List<Topic> topics, Activity activity) {
        this.topics = topics;
        this.activity = activity;
    }

    @Override
    public GalleryViewHolder onCreateViewHolder(final ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_gallery, viewGroup, false);
        return new GalleryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GalleryViewHolder viewHolder, int i) {
        Topic topic = topics.get(i);
        viewHolder.getTitle().setText(Html.fromHtml(topic.getTitle()));
        //galleryViewHolder.getCategory().setText(item.getCategory());

        String url = StringUtils.clearUrl(topic.getUrl());
        Picasso.with(activity).cancelRequest(viewHolder.getImage());
        String imageUrl = "https://linux.org.ru/gallery" + url.substring(url.lastIndexOf("/")) + "-med.jpg";
        Picasso.with(activity).load(imageUrl).resize(400, 0).into((viewHolder.getImage()));

        if (topic.getTags().size() == 0) {
            viewHolder.getTags().setVisibility(View.GONE);
        } else viewHolder.getTags().setText(TextUtils.join(", ", topic.getTags()));

        //viewHolder.getCommentsCount().setText(item.getCommentsCount());
    }

    @Override
    public int getItemCount() {
        return topics.size();
    }
}
