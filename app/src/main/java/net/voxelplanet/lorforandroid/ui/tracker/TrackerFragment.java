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

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import net.voxelplanet.lorforandroid.R;
import net.voxelplanet.lorforandroid.api.ApiManager;
import net.voxelplanet.lorforandroid.api.ApiTracker;
import net.voxelplanet.lorforandroid.model.TrackerItem;
import net.voxelplanet.lorforandroid.model.TrackerItems;
import net.voxelplanet.lorforandroid.ui.base.BaseCallbackFragment;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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
        getListItems();
    }

    @Override
    protected String getUrl(int position) {
        return items.get(position).getUrl();
    }

    @Override
    protected void getListItems() {
        ApiManager.API_MANAGER.getApiTracker().getTracker(offset, filter, new Callback<TrackerItems>() {
            @Override
            public void success(TrackerItems trackerItems, Response response) {
                if (trackerItems.trackerItems.size() > 0) {
                    offset += 30;
                    items.addAll(trackerItems.trackerItems);

                    // Next lines are here to remove duplicate topics from tracker due to linux.org.ru engine bug
                    Set<TrackerItem> trackerItemSet = new LinkedHashSet<TrackerItem>(items);
                    items.clear();
                    items.addAll(trackerItemSet);

                    adapter.notifyDataSetChanged();
                    swipeRefreshLayout.setRefreshing(false);
                } else {
                    Toast.makeText(activity, R.string.error_no_more_data, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(activity, R.string.error_network, Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
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
        return new TrackerAdapter(items, activity);
    }

}
