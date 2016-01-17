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

package io.github.getsmp.lorforandroid.ui.base;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.github.getsmp.lorforandroid.R;
import io.github.getsmp.lorforandroid.api.ApiManager;
import io.github.getsmp.lorforandroid.api.ApiTopic;
import io.github.getsmp.lorforandroid.model.Topic;
import io.github.getsmp.lorforandroid.model.TopicItems;
import io.github.getsmp.lorforandroid.ui.section.SectionEnum;
import io.github.getsmp.lorforandroid.util.StringUtils;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public abstract class BaseTopicsFragment extends BaseCallbackFragment {
    protected final List<Topic> items = new ArrayList<Topic>();
    private int offset;

    @Override
    protected String getUrl(int position) {
        return items.get(position).getUrl();
    }

    @Override
    public void getListItems() {
        startRefresh();
        ApiManager.INSTANCE.apiRestAdapter.create(ApiTopic.class).getTopics(getSection().name(), null, "2007-01-01", StringUtils.getCurrentDate(), null, offset, null, null, null, null, new Callback<TopicItems>() {
            @Override
            public void success(TopicItems topicItems, Response response) {
                if (topicItems.topicItems.size() > 0) {
                    offset += 30;
                    items.addAll(topicItems.topicItems);
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(context, R.string.error_no_more_data, Toast.LENGTH_SHORT).show();
                }
                stopRefresh();
            }

            @Override
            public void failure(RetrofitError error) {
                networkError();
            }
        });
    }

    @Override
    protected void clearData() {
        offset = 0;
        items.clear();
    }

    protected abstract SectionEnum getSection();
}
