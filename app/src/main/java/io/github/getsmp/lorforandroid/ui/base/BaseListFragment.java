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
