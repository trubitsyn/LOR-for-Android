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

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.github.getsmp.lorforandroid.R;
import io.github.getsmp.lorforandroid.api.ApiManager;
import io.github.getsmp.lorforandroid.model.Topic;
import io.github.getsmp.lorforandroid.model.Topics;
import io.github.getsmp.lorforandroid.ui.ImageActivity;
import io.github.getsmp.lorforandroid.ui.base.BaseFragment;
import io.github.getsmp.lorforandroid.util.StringUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopicFragment extends BaseFragment {
    @Bind(R.id.topicScrollView) NestedScrollView scrollView;
    @Bind(R.id.progressBar) ProgressBar progressBar;
    @Bind(R.id.errorView) TextView errorView;
    @Bind(R.id.topicTitle) TextView title;
    @Bind(R.id.topicTags) TextView tags;
    @Bind(R.id.topicAuthor) TextView author;
    @Bind(R.id.topicDate) TextView date;
    @Bind(R.id.topicImage) @Nullable ImageView image;
    @Bind(R.id.topicMessage) TextView message;
    private String url;
    private String imageUrl;
    private Topic topic;

    public static TopicFragment newInstance(String url, String imageUrl) {
        TopicFragment fragment = new TopicFragment();
        Bundle args = new Bundle();
        args.putString("url", url);
        args.putString("imageUrl", imageUrl);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        url = getArguments().getString("url");
        imageUrl = getArguments().getString("imageUrl");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_topic, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState == null) {
            loadTopic();
        } else {
            loadingEnded();
            setTopic();
        }
    }

    private void loadTopic() {
        if (StringUtils.isClub(url)) {
            loadingError(R.string.error_access_denied);
        } else {
            Call<Topics> topics = ApiManager.INSTANCE.getApiTopic().getTopic(url);
            topics.enqueue(new Callback<Topics>() {
                @Override
                public void onResponse(Call<Topics> call, Response<Topics> response) {
                    if (response.body() != null) {
                        topic = response.body().topic;
                        loadingEnded();
                        setTopic();
                    } else {
                        loadingError(R.string.error_network);
                    }
                }

                @Override
                public void onFailure(Call<Topics> call, Throwable t) {
                    loadingError(R.string.error_network);
                }
            });
        }
    }

    private void setTopic() {
        title.setText(Html.fromHtml(topic.getTitle()));
        List<String> tagsList = topic.getTags();
        if (!tagsList.isEmpty()) {
            tags.setVisibility(View.VISIBLE);
            tags.setText(StringUtils.tagsFromStrings(tagsList));
        } else tags.setVisibility(View.GONE);

        if (image != null) {
            Glide.with(TopicFragment.this)
                    .load(imageUrl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into((image));

            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), ImageActivity.class);
                    intent.putExtra("imageUrl", imageUrl);
                    startActivity(intent);
                }
            });
        }

        author.setText(topic.getAuthor().getNick());
        date.setText(StringUtils.getDate(topic.getPostDate()));
        message.setText(Html.fromHtml(topic.getMessage()));
        message.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void loadingEnded() {
        if (progressBar != null) progressBar.setVisibility(View.GONE);
        scrollView.setVisibility(View.VISIBLE);
    }

    private void loadingError(int stringResource) {
        scrollView.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        errorView.setText(stringResource);
        errorView.setVisibility(View.VISIBLE);
    }
}
