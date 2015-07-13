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
import android.support.design.widget.TabLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import net.voxelplanet.lorforandroid.R;
import net.voxelplanet.lorforandroid.api.Adapter;
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
    private final TabListener tabListener = new TabListener();
    private int offset;
    private String filter = TrackerFilterEnum.all.name();
    private TabLayout tabLayout;
    private List<TrackerItem> items = new ArrayList<TrackerItem>();

    @Override
    protected String getUrl(int position) {
        return items.get(position).getUrl();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        if (view != null) {
            tabLayout = (TabLayout) view.findViewById(R.id.trackerTabs);
            initTabs();
        }
        return view;
    }

    @Override
    public void getListItems() {
        ApiTracker apiTracker = Adapter.restAdapter.create(ApiTracker.class);
        apiTracker.getTracker(offset, filter, new Callback<TrackerItems>() {
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
        tabListener.onTabUnselected(null);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_tracker;
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        return new TrackerAdapter(items, activity);
    }

    private void initTabs() {
        tabLayout.addTab(tabLayout.newTab().setText("Все"));
        tabLayout.addTab(tabLayout.newTab().setText("Основные"));
        tabLayout.addTab(tabLayout.newTab().setText("Без talks"));
        tabLayout.addTab(tabLayout.newTab().setText("Технические"));

        tabLayout.setOnTabSelectedListener(new TabListener());
    }

    private class TabListener implements TabLayout.OnTabSelectedListener {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            switch (tab.getPosition()) {
                case 0:
                    filter = TrackerFilterEnum.all.name();
                    break;
                case 1:
                    filter = TrackerFilterEnum.main.name();
                    break;
                case 2:
                    filter = TrackerFilterEnum.notalks.name();
                    break;
                case 3:
                    filter = TrackerFilterEnum.tech.name();
                    break;
            }

            getListItems();
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {
            offset = 0;
            items.clear();
        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {
        }
    }
}
