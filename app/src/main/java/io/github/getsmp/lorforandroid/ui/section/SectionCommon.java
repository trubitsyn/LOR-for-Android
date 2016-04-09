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

public abstract class SectionCommon extends BaseListFragment {
    protected int offset;
    private final ItemFactory itemFactory = getItemFactory();
    private final String path = getPath();
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
            NetworkClient.get(path + "/", getRequestParams(), handler);
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
