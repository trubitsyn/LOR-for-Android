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
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import net.voxelplanet.lorforandroid.R;
import net.voxelplanet.lorforandroid.api.Adapter;
import net.voxelplanet.lorforandroid.api.ApiSection;
import net.voxelplanet.lorforandroid.model.Topic;
import net.voxelplanet.lorforandroid.model.TopicItems;
import net.voxelplanet.lorforandroid.ui.util.DividerItemDecoration;
import net.voxelplanet.lorforandroid.ui.util.ItemClickCallback;
import net.voxelplanet.lorforandroid.ui.util.ItemClickListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class GalleryFragment extends Fragment {
    private final List<Topic> items = new ArrayList<Topic>();
    private RecyclerView topicsView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Activity activity;
    private RecyclerView.Adapter adapter;
    private ItemClickCallback callback;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
        this.callback = (ItemClickCallback) activity;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_topics, container, false);
        topicsView = (RecyclerView) view.findViewById(R.id.topicsView);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        topicsView.setLayoutManager(layoutManager);
        topicsView.addItemDecoration(new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL_LIST));

        topicsView.addOnItemTouchListener(new ItemClickListener(activity, new ItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                callback.onItemClicked(items.get(topicsView.getChildPosition(view)).getUrl());
            }
        }));

        // TODO: Load while scrolling

        adapter = new GalleryAdapter(items, activity);
        topicsView.setAdapter(adapter);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getListItems();
            }
        });

        return view;
    }

    public void getListItems() {
        ApiSection apiSection = Adapter.restAdapter.create(ApiSection.class);
        String start = "2007-01-01";
        String today = new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(new Date());

        apiSection.getTopics("gallery", null, start, today, null, null, null, null, null, null, new Callback<TopicItems>() {
            @Override
            public void success(TopicItems topicItems, Response response) {
                items.addAll(topicItems.topicItems);
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(activity, R.string.error_network, Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}
