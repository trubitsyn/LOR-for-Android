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

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;

import com.loopj.android.http.RequestParams;

import io.github.getsmp.lorforandroid.R;
import io.github.getsmp.lorforandroid.ui.section.Item;
import io.github.getsmp.lorforandroid.ui.section.ItemFactory;
import io.github.getsmp.lorforandroid.ui.section.SectionFragment;
import io.github.getsmp.lorforandroid.ui.section.gallery.GalleryItem;
import io.github.getsmp.lorforandroid.ui.section.gallery.GalleryUtils;
import io.github.getsmp.lorforandroid.ui.util.ItemClickCallback;
import io.github.getsmp.lorforandroid.ui.util.SpinnerViewUtils;

public class TrackerFragment extends SectionFragment {
    private int filter;

    public static TrackerFragment newInstance(TrackerFilterEnum trackerFilterEnum) {
        TrackerFragment trackerFragment = new TrackerFragment();
        Bundle args = new Bundle();
        args.putInt("filter", trackerFilterEnum.ordinal());
        trackerFragment.setArguments(args);
        return trackerFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        filter = getArguments().getInt("filter");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SpinnerViewUtils.setSpinnerView(getActivity(), R.array.tracker_spinner, filter, new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filter = position;
                restart();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    @Override
    protected ItemFactory getItemFactory() {
        return new TrackerItemFactory();
    }

    @Override
    public int getItemsPerPage() {
        return 30;
    }

    @Override
    public int getMaxOffset() {
        return 180;
    }

    @Override
    public String getPath() {
        return "tracker";
    }

    @Override
    public RequestParams getRequestParams() {
        return new RequestParams("offset", offset, "filter", TrackerFilterEnum.values()[filter].name());
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        return new TrackerAdapter(items);
    }

    @Override
    protected void onItemClickCallback(int position) {
        Item item = (Item) items.get(position);
        if (GalleryUtils.isGalleryUrl(item.getUrl())) {
            String imagesUrl = GalleryUtils.getGalleryImagesUrl(item.getUrl());
            String medium2xImageUrl = GalleryUtils.getMedium2xImageUrl(imagesUrl);
            String mediumImageUrl = GalleryUtils.getMediumImageUrl(imagesUrl);

            // TODO: Url of high-res image in GalleryItem
            // Currently cannot get it because images can either have .png extension or .jpg and there's no way to determine the correct without issuing a HTTP request.
            GalleryItem galleryItem = new GalleryItem(item.getUrl(), item.getTitle(), item.getGroupTitle(), item.getDate(), item.getTags(), item.getAuthor(), item.getComments(), medium2xImageUrl, medium2xImageUrl, mediumImageUrl);

            ((ItemClickCallback) context).onGalleryTopicRequested(galleryItem);
        } else {
            ((ItemClickCallback) context).onTopicRequested(item.getUrl());
        }
    }
}
