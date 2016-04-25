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
