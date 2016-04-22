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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.Bind;
import io.github.getsmp.lorforandroid.R;

public abstract class LoadableFragment extends BaseFragment {
    @Bind(R.id.progressBar) protected ProgressBar progressBar;
    @Bind(R.id.errorView) TextView errorView;

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
                restart();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void restart() {
        hideAllShowProgressView();
        resetState();
        fetchData();
    }

    private void hideAllShowProgressView() {
        dataView().setVisibility(View.GONE);
        errorView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    protected void stopRefresh() {
        if (progressBar != null) progressBar.setVisibility(View.GONE);
    }

    protected void stopRefreshAndShow() {
        stopRefresh();
        if (dataView() != null) dataView().setVisibility(View.VISIBLE);
    }

    protected void showErrorView(int stringResource) {
        stopRefresh();
        if (errorView != null) {
            dataView().setVisibility(View.INVISIBLE);
            errorView.setVisibility(View.VISIBLE);
            errorView.setText(stringResource);
        }
    }

    protected void resetState() {
        // Implement in child classes
    }

    protected abstract void fetchData();

    protected abstract View dataView();
}
