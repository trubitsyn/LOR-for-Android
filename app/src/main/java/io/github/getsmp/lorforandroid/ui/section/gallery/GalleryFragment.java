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

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;

import com.loopj.android.http.RequestParams;

import io.github.getsmp.lorforandroid.R;
import io.github.getsmp.lorforandroid.ui.section.ItemFactory;
import io.github.getsmp.lorforandroid.ui.section.SectionCommon;
import io.github.getsmp.lorforandroid.ui.util.ItemClickCallback;
import io.github.getsmp.lorforandroid.ui.util.SpinnerViewUtils;

public class GalleryFragment extends SectionCommon {
    private int filter;

    public static GalleryFragment newInstance(GalleryFilterEnum galleryFilterEnum) {
        GalleryFragment galleryFragment = new GalleryFragment();
        Bundle args = new Bundle();
        args.putInt("filter", galleryFilterEnum.ordinal());
        galleryFragment.setArguments(args);
        return galleryFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        filter = getArguments().getInt("filter");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SpinnerViewUtils.setSpinnerView(getActivity(), R.array.gallery_spinner, filter, new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filter = position;
                restart();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private boolean isAll() {
        return filter == GalleryFilterEnum.all.ordinal();
    }

    @Override
    protected ItemFactory getItemFactory() {
        return new GalleryItemFactory();
    }

    @Override
    public int getItemsPerPage() {
        return 20;
    }

    @Override
    public int getMaxOffset() {
        return 200;
    }

    @Override
    public String getPath() {
        String path = isAll() ? "" : "/" + GalleryFilterEnum.values()[filter].name();
        return "gallery" + path;
    }

    @Override
    public RequestParams getRequestParams() {
        return new RequestParams("offset", offset);
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        return new GalleryAdapter(items, context);
    }

    @Override
    protected void onItemClickCallback(int position) {
        GalleryItem item = (GalleryItem) items.get(position);
        ((ItemClickCallback) context).onGalleryTopicRequested(item);
    }
}
