/*
 * Copyright (C) 2015-2016 Nikola Trubitsyn
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

package dev.trubitsyn.lorforandroid.ui.topic;

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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import dev.trubitsyn.lorforandroid.R;
import dev.trubitsyn.lorforandroid.api.ApiManager;
import dev.trubitsyn.lorforandroid.api.model.Topic;
import dev.trubitsyn.lorforandroid.api.model.Topics;
import dev.trubitsyn.lorforandroid.ui.ImageActivity;
import dev.trubitsyn.lorforandroid.ui.base.LoadableFragment;
import dev.trubitsyn.lorforandroid.util.DateUtils;
import dev.trubitsyn.lorforandroid.util.PreferenceUtils;
import dev.trubitsyn.lorforandroid.util.StringUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopicFragment extends LoadableFragment {
    @Bind(R.id.topicScrollView) NestedScrollView scrollView;
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
        setHasOptionsMenu(false);
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
            fetchData();
        } else {
            stopRefreshAndShow();
            setTopic();
        }
    }

    @Override
    protected void fetchData() {
        if (StringUtils.isClub(url)) {
            showErrorView(R.string.error_access_denied);
        } else {
            Call<Topics> topics = ApiManager.INSTANCE.getApiTopic().getTopic(url);
            topics.enqueue(new Callback<Topics>() {
                @Override
                public void onResponse(Call<Topics> call, Response<Topics> response) {
                    if (response.body() != null) {
                        topic = response.body().topic;
                        stopRefreshAndShow();
                        setTopic();
                    } else {
                        showErrorView(R.string.error_network);
                    }
                }

                @Override
                public void onFailure(Call<Topics> call, Throwable t) {
                    showErrorView(R.string.error_network);
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
            if (PreferenceUtils.shouldLoadImagesNow(getActivity())) {
                loadImageAndSetImageActivityListener();
            } else {
                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadImageAndSetImageActivityListener();
                    }
                });
            }
        }

        author.setText(topic.getAuthor().getNick());
        date.setText(DateUtils.getDate(topic.getPostDate()));
        message.setText(Html.fromHtml(topic.getMessage()));
        message.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void loadImageAndSetImageActivityListener() {
        assert (image) != null;
        image.setImageDrawable(null);
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

    @Override
    protected View dataView() {
        return scrollView;
    }
}
