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

package io.github.getsmp.lorforandroid.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.github.getsmp.lorforandroid.R;
import io.github.getsmp.lorforandroid.ui.util.DividerItemDecoration;
import io.github.getsmp.lorforandroid.ui.util.InfiniteScrollListener;
import io.github.getsmp.lorforandroid.ui.util.ItemClickListener;

public abstract class BaseListFragment extends RefreshableFragment {
    protected Context context;
    protected RecyclerView.Adapter adapter;
    private InfiniteScrollListener scrollListener;
    protected final List items = new ArrayList();
    @Bind(R.id.recyclerView) protected RecyclerView recyclerView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_swiperefresh_recyclerview, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        if (showDividers()) recyclerView.addItemDecoration(new DividerItemDecoration(context));

        scrollListener = new InfiniteScrollListener(layoutManager) {
            @Override
            public void onLoadMore() {
                if (loadMoreAllowed()) fetchData();
            }
        };

        recyclerView.addOnScrollListener(scrollListener);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            initAdapter();
            stopRefreshAndShow();
        } else {
            initAdapter();
            fetchData();
        }
    }

    @Override
    protected void resetState() {
        clearData();
        scrollListener.reset();
    }

    @Override
    protected void restart() {
        recyclerView.scrollToPosition(0);
        super.restart();
    }

    protected void showUserFriendlyError(int errorString) {
        if (items.size() > 0) {
            Toast.makeText(context, errorString, Toast.LENGTH_SHORT).show();
        } else showErrorView(errorString);
    }

    private void initAdapter() {
        adapter = getAdapter();
        recyclerView.setAdapter(adapter);
    }

    protected boolean loadMoreAllowed() {
        return true;
    }

    protected boolean showDividers() {
        return true;
    }

    protected void clearData() {
        items.clear();
    }

    protected void setOnClickListener(ItemClickListener.OnItemClickListener listener) {
        recyclerView.addOnItemTouchListener(new ItemClickListener(context, listener));
    }

    protected abstract RecyclerView.Adapter getAdapter();
}
