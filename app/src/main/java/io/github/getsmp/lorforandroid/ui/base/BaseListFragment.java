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
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.github.getsmp.lorforandroid.R;
import io.github.getsmp.lorforandroid.ui.util.DividerItemDecoration;
import io.github.getsmp.lorforandroid.ui.util.InfiniteScrollListener;

public abstract class BaseListFragment extends Fragment {
    protected Context context;
    protected RecyclerView.Adapter adapter;
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
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_swiperefresh_recyclerview, container, false);
        ButterKnife.bind(this, view);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        if (showDividers()) recyclerView.addItemDecoration(new DividerItemDecoration(context));

        adapter = getAdapter();
        recyclerView.setAdapter(adapter);

        final InfiniteScrollListener scrollListener = new InfiniteScrollListener(layoutManager) {
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

        getListItems();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    protected boolean loadMoreAllowed() {
        return true;
    }

    protected boolean showDividers() {
        return true;
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
        if (recyclerView != null) {
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    protected void showErrorView(int stringResource) {
        stopRefresh();
        errorView.setVisibility(View.VISIBLE);
        errorView.setText(stringResource);
    }
}
