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
