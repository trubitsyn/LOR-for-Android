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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.github.getsmp.lorforandroid.R;
import io.github.getsmp.lorforandroid.api.ApiManager;
import io.github.getsmp.lorforandroid.api.ApiTracker;
import io.github.getsmp.lorforandroid.model.TrackerItem;
import io.github.getsmp.lorforandroid.model.TrackerItems;
import io.github.getsmp.lorforandroid.ui.base.BaseCallbackFragment;
import io.github.getsmp.lorforandroid.ui.util.ItemClickCallback;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class TrackerFragment extends BaseCallbackFragment {
    private final List<TrackerItem> items = new ArrayList<TrackerItem>();
    private int offset;
    private String filter;

    public static TrackerFragment newInstance(TrackerFilterEnum trackerFilterEnum) {
        TrackerFragment trackerFragment = new TrackerFragment();
        Bundle args = new Bundle();
        args.putString("filter", trackerFilterEnum.name());
        trackerFragment.setArguments(args);
        return trackerFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        filter = getArguments().getString("filter");
    }

    @Override
    protected void getListItems() {
        ApiManager.INSTANCE.apiRestAdapter.create(ApiTracker.class).getTracker(offset, filter, new Callback<TrackerItems>() {
            @Override
            public void success(TrackerItems trackerItems, Response response) {
                if (trackerItems.trackerItems.size() > 0) {
                    offset += 30;

                    items.addAll(trackerItems.trackerItems);
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(context, R.string.error_no_more_data, Toast.LENGTH_SHORT).show();
                }
                stopRefresh();
            }

            @Override
            public void failure(RetrofitError error) {
                showErrorView(R.string.error_network);
            }
        });
    }

    @Override
    protected void clearData() {
        offset = 0;
        items.clear();
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        return new TrackerAdapter(items);
    }

    @Override
    protected void onItemClickCallback(int position) {
        ((ItemClickCallback) context).onTopicRequested(items.get(position).getUrl());
    }
}
