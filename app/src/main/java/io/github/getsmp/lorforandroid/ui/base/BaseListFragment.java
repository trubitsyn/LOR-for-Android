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
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.github.getsmp.lorforandroid.R;
import io.github.getsmp.lorforandroid.ui.util.DividerItemDecoration;
import io.github.getsmp.lorforandroid.ui.util.InfiniteScrollListener;
import io.github.getsmp.lorforandroid.ui.util.ItemClickListener;

public abstract class BaseListFragment extends BaseFragment {
    protected Context context;
    protected RecyclerView.Adapter adapter;
    private InfiniteScrollListener scrollListener;
    protected List items = new ArrayList();
    @Bind(R.id.swipeRefreshLayout) protected SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.recyclerView) protected RecyclerView recyclerView;
    @Bind(R.id.progressBar) protected ProgressBar progressBar;
    @Bind(R.id.errorView) TextView errorView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.refresh, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refreshButton:
                recyclerView.scrollToPosition(0);
                swipeRefreshLayout.setVisibility(View.GONE);
                errorView.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                clearData();
                scrollListener.reset();
                getListItems();
                return true;
        }
        return super.onOptionsItemSelected(item);
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
                if (loadMoreAllowed()) {
                    getListItems();
                }
            }
        };

        recyclerView.addOnScrollListener(scrollListener);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                clearData();
                scrollListener.reset();
                errorView.setVisibility(View.GONE);
                getListItems();
            }
        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            initAdapter();
            stopRefreshAndShow();
        } else {
            initAdapter();
            getListItems();
        }
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

    protected void setRefreshable(boolean refreshable) {
        swipeRefreshLayout.setEnabled(refreshable);
    }

    protected abstract void getListItems();

    protected abstract void clearData();

    protected abstract RecyclerView.Adapter getAdapter();

    protected void stopRefresh() {
        // swipeRefreshLayout still might be null
        if (swipeRefreshLayout != null) {
            // SwipeRefreshLayout never stops refreshing regardless of null it is or not
            swipeRefreshLayout.setRefreshing(false);
        }
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
    }

    protected void stopRefreshAndShow() {
        stopRefresh();
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setVisibility(View.VISIBLE);
        }
    }

    protected void showErrorView(int stringResource) {
        stopRefresh();
        if (errorView != null) {
            swipeRefreshLayout.setVisibility(View.INVISIBLE);
            errorView.setVisibility(View.VISIBLE);
            errorView.setText(stringResource);
        }
    }

    protected void setOnClickListener(ItemClickListener.OnItemClickListener listener) {
        recyclerView.addOnItemTouchListener(new ItemClickListener(context, listener));
    }
}
