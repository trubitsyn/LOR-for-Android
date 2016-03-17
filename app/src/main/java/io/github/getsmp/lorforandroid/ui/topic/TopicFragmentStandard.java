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

package io.github.getsmp.lorforandroid.ui.topic;

import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import io.github.getsmp.lorforandroid.R;
import io.github.getsmp.lorforandroid.api.ApiManager;
import io.github.getsmp.lorforandroid.api.ApiTopic;
import io.github.getsmp.lorforandroid.model.Topic;
import io.github.getsmp.lorforandroid.model.Topics;
import io.github.getsmp.lorforandroid.util.StringUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopicFragmentStandard extends TopicFragmentCommon {
    @Bind(R.id.topicTitle) TextView title;
    @Bind(R.id.topicTags) TextView tags;
    @Bind(R.id.topicAuthor) TextView author;
    @Bind(R.id.topicMessage) TextView message;
    private String url;

    public static TopicFragmentStandard newInstance(String url) {
        TopicFragmentStandard fragment = new TopicFragmentStandard();
        Bundle args = new Bundle();
        args.putString("url", url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url = getArguments().getString("url");
    }

    @Override
    protected void loadTopic() {
        loadingStarted();

        Call<Topics> topics = ApiManager.INSTANCE.apiRestAdapter.create(ApiTopic.class).getTopic(url);
        topics.enqueue(new Callback<Topics>() {
            @Override
            public void onResponse(Call<Topics> call, Response<Topics> response) {
                if (response.body() != null) {
                    Topic topic = response.body().topic;
                    title.setText(Html.fromHtml(topic.getTitle()));
                    List<String> tagsList = topic.getTags();
                    if (!tagsList.isEmpty()) {
                        tags.setVisibility(View.VISIBLE);
                        tags.setText(StringUtils.tagsFromStrings(tagsList));
                    } else tags.setVisibility(View.GONE);

                    author.setText(topic.getAuthor().getNick());
                    message.setText(Html.fromHtml(topic.getMessage()));
                    message.setMovementMethod(LinkMovementMethod.getInstance());
                }

                loadingEnded();
            }

            @Override
            public void onFailure(Call<Topics> call, Throwable t) {
                loadingError();
            }
        });
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_topic_standard;
    }
}
