/*
 * Copyright 2015 getsmp
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

package net.voxelplanet.lorforandroid.ui.base;

import android.widget.Toast;

import net.voxelplanet.lorforandroid.R;
import net.voxelplanet.lorforandroid.api.Adapter;
import net.voxelplanet.lorforandroid.api.ApiSection;
import net.voxelplanet.lorforandroid.model.Topic;
import net.voxelplanet.lorforandroid.model.TopicItems;
import net.voxelplanet.lorforandroid.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public abstract class BaseTopicsFragment extends BaseCallbackFragment {
    protected List<Topic> items = new ArrayList<Topic>();

    @Override
    protected String getUrl(int position) {
        return items.get(position).getUrl();
    }

    @Override
    public void getListItems() {
        ApiSection apiSection = Adapter.restAdapter.create(ApiSection.class);
        apiSection.getTopics(getSection(), null, "2007-01-01", StringUtils.getCurrentDate(), null, null, null, null, null, null, new Callback<TopicItems>() {
            @Override
            public void success(TopicItems topicItems, Response response) {
                items.addAll(topicItems.topicItems);
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(activity, R.string.error_network, Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    protected void clearData() {
        // Clear offset etc.
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_swiperefresh_recyclerview;
    }

    protected abstract String getSection();
}
