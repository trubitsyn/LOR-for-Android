/*
 *
 *  * Copyright 2016 getsmp
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *     http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package io.github.getsmp.lorforandroid.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import butterknife.Bind;
import io.github.getsmp.lorforandroid.R;

public abstract class RefreshableFragment extends LoadableFragment {
    @Bind(R.id.swipeRefreshLayout) protected SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                resetState();
                errorView.setVisibility(View.GONE);
                fetchData();
            }
        });
    }

    @Override
    protected void stopRefresh() {
        super.stopRefresh();
        // swipeRefreshLayout still might be null
        if (swipeRefreshLayout != null) swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    protected View dataView() {
        return swipeRefreshLayout;
    }
}
