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

package io.github.getsmp.lorforandroid.ui.section;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import io.github.getsmp.lorforandroid.R;
import io.github.getsmp.lorforandroid.ui.base.BaseListFragment;
import io.github.getsmp.lorforandroid.ui.util.ItemClickListener;
import io.github.getsmp.lorforandroid.util.NetworkClient;

public abstract class SectionFragment extends BaseListFragment {
    protected int offset;
    private final ItemFactory itemFactory = getItemFactory();
    private final int maxOffset = getMaxOffset();
    private final AsyncHttpResponseHandler handler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            String resp = null;
            try {
                resp = new String(responseBody, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                // Will never execute
            }

            try {
                Element body = Jsoup.parse(resp).body();
                itemFactory.prepareItems(body, items);
            } catch (NullPointerException e) {
                showUserFriendlyError(R.string.error_npe);
                return;
            }

            offset += getItemsPerPage();
            adapter.notifyDataSetChanged();
            stopRefreshAndShow();
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            showUserFriendlyError(R.string.error_network);
        }
    };

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setOnClickListener(new ItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view) {
                onItemClickCallback(recyclerView.getChildAdapterPosition(view));
            }
        });
    }

    @Override
    protected void fetchData() {
        if (offset <= maxOffset) {
            NetworkClient.get(getPath() + "/", getRequestParams(), handler);
        } else Toast.makeText(context, R.string.error_no_more_data, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void clearData() {
        super.clearData();
        offset = 0;
    }

    protected abstract ItemFactory getItemFactory();

    public abstract int getItemsPerPage();

    public abstract int getMaxOffset();

    public abstract String getPath();

    public abstract RequestParams getRequestParams();

    protected abstract void onItemClickCallback(int position);
}
