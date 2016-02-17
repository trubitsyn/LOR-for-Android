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

package io.github.getsmp.lorforandroid.ui.topic;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.github.getsmp.lorforandroid.R;
import io.github.getsmp.lorforandroid.api.ApiManager;
import io.github.getsmp.lorforandroid.api.ApiTopic;
import io.github.getsmp.lorforandroid.model.Topic;
import io.github.getsmp.lorforandroid.model.Topics;
import io.github.getsmp.lorforandroid.util.StringUtils;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class TopicFragment extends Fragment {
    @Bind(R.id.topicTitle) TextView title;
    @Bind(R.id.topicTags) TextView tags;
    @Bind(R.id.topicAuthor) TextView author;
    @Bind(R.id.topicMessage) TextView message;
    @Bind(R.id.topicScrollView) ScrollView scrollView;
    @Bind(R.id.progressBar) ProgressBar progressBar;
    @Bind(R.id.errorView) TextView errorView;
    private String url;

    public static TopicFragment newInstance(String url) {
        TopicFragment fragment = new TopicFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_topic, container, false);
        ButterKnife.bind(this, view);
        loadTopic(url);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void loadTopic(String url) {
        this.url = url;
        errorView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        ApiManager.INSTANCE.apiRestAdapter.create(ApiTopic.class).getTopic(url, new Callback<Topics>() {
            @Override
            public void success(Topics topics, Response response) {
                Topic topic = topics.topic;
                title.setText(Html.fromHtml(topic.getTitle()));
                List<String> tagsList = topic.getTags();
                if (!tagsList.isEmpty()) {
                    tags.setVisibility(View.VISIBLE);
                    tags.setText(StringUtils.tagsFromStrings(tagsList));
                } else tags.setVisibility(View.GONE);
                author.setText(topic.getAuthor().getNick());
                message.setText(Html.fromHtml(topic.getMessage()));
                message.setMovementMethod(LinkMovementMethod.getInstance());
                progressBar.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);
            }

            @Override
            public void failure(RetrofitError error) {
                progressBar.setVisibility(View.GONE);
                errorView.setText(R.string.error_network);
                errorView.setVisibility(View.VISIBLE);
            }
        });
    }
}
