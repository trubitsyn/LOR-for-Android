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

package io.github.getsmp.lorforandroid.ui.section.forum;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;

import io.github.getsmp.lorforandroid.R;
import io.github.getsmp.lorforandroid.ui.section.ItemFactory;
import io.github.getsmp.lorforandroid.ui.section.SectionCommon;
import io.github.getsmp.lorforandroid.ui.util.ItemClickCallback;
import io.github.getsmp.lorforandroid.util.StringUtils;

public class ForumOverviewFragment extends SectionCommon {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
    }

    @Override
    public int getItemsPerPage() {
        return 0;
    }

    @Override
    public int getMaxOffset() {
        return 0;
    }

    @Override
    public String getPath() {
        return "forum";
    }

    @Override
    public RequestParams getRequestParams() {
        return null;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeRefreshLayout.setEnabled(false);
    }

    @Override
    protected ItemFactory getItemFactory() {
        return new ForumOverviewItemFactory();
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        return new ForumOverviewAdapter(items);
    }

    @Override
    protected void onItemClickCallback(int position) {
        ForumOverviewItem item = (ForumOverviewItem) items.get(position);
        if (StringUtils.isClub(item.getUrl())) {
            Toast.makeText(context, R.string.error_access_denied, Toast.LENGTH_SHORT).show();
        } else {
            ((ItemClickCallback) context).onForumSectionRequested(item.getUrl(), item.getName());
        }
    }

    @Override
    protected boolean loadMoreAllowed() {
        return false;
    }

    @Override
    protected boolean showDividers() {
        return false;
    }

}
