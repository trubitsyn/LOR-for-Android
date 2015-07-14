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

package net.voxelplanet.lorforandroid.ui.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.voxelplanet.lorforandroid.R;
import net.voxelplanet.lorforandroid.ui.util.DividerItemDecoration;
import net.voxelplanet.lorforandroid.ui.util.InfiniteScrollListener;

public abstract class BaseListFragment extends Fragment {
    protected Activity activity;
    protected RecyclerView.Adapter adapter;
    protected SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View newView = inflater.inflate(getLayout(), container, false);
        recyclerView = (RecyclerView) newView.findViewById(R.id.recyclerView);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL_LIST));

        adapter = getAdapter();
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new InfiniteScrollListener(layoutManager) {
            @Override
            public void onLoadMore() {
                getListItems();
            }
        });

        swipeRefreshLayout = (SwipeRefreshLayout) newView.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                clearData();
                getListItems();
            }
        });

        return newView;
    }

    protected abstract void getListItems();
    protected abstract void clearData();
    protected abstract int getLayout();
    protected abstract RecyclerView.Adapter getAdapter();
}
