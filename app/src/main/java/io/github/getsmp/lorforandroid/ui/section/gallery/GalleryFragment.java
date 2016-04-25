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

package io.github.getsmp.lorforandroid.ui.section.gallery;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;

import com.loopj.android.http.RequestParams;

import io.github.getsmp.lorforandroid.R;
import io.github.getsmp.lorforandroid.ui.section.ItemFactory;
import io.github.getsmp.lorforandroid.ui.section.SectionFragment;
import io.github.getsmp.lorforandroid.ui.util.ItemClickCallback;
import io.github.getsmp.lorforandroid.ui.util.SpinnerViewUtils;

public class GalleryFragment extends SectionFragment {
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
