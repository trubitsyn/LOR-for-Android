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

package net.voxelplanet.lorforandroid.ui.news;

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
import java.util.Locale;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class NewsFragment extends Fragment {
    private final ArrayList<Topic> items = new ArrayList<Topic>();
    private RecyclerView newsView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView.Adapter adapter;
    private ItemClickCallback callback;
    private Activity activity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
        this.callback = (ItemClickCallback) activity;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        newsView = (RecyclerView) view.findViewById(R.id.newsView);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        newsView.setLayoutManager(layoutManager);
        newsView.addItemDecoration(new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL_LIST));

        newsView.addOnItemTouchListener(new ItemClickListener(activity, new ItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view) {
                callback.onItemClicked(items.get(newsView.getChildPosition(view)).getUrl());
            }
        }));

        // TODO: Load while scrolling

        adapter = new NewsAdapter(items);
        newsView.setAdapter(adapter);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.newsSwipeRefreshLayout);
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

        apiSection.getTopics("news", null, start, today, null, null, null, null, null, null, new Callback<TopicItems>() {
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
