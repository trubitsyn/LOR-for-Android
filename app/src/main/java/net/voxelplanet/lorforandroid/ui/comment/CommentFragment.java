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

package net.voxelplanet.lorforandroid.ui.comment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import net.voxelplanet.lorforandroid.R;
import net.voxelplanet.lorforandroid.api.Adapter;
import net.voxelplanet.lorforandroid.api.ApiComments;
import net.voxelplanet.lorforandroid.model.Comment;
import net.voxelplanet.lorforandroid.model.Comments;
import net.voxelplanet.lorforandroid.ui.util.DividerItemDecoration;
import net.voxelplanet.lorforandroid.ui.util.InfiniteScrollListener;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class CommentFragment extends Fragment {
    private final List<Comment> comments = new ArrayList<Comment>();
    private SwipeRefreshLayout swipeRefreshLayout;
    private String url;
    private int currentPage, previousCount = 0;
    private RecyclerView.Adapter adapter;
    private Activity activity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comments, container, false);
        RecyclerView commentView = (RecyclerView) view.findViewById(R.id.comments);
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        commentView.setLayoutManager(layoutManager);
        commentView.addItemDecoration(new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL_LIST));
        commentView.setOnScrollListener(new InfiniteScrollListener(layoutManager) {
            @Override
            public void onLoadMore() {
                loadComments(url);
            }
        });

        adapter = new CommentAdapter(comments, activity, (CommentClickListener) activity);
        commentView.setAdapter(adapter);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currentPage = 0;
                previousCount = 0;
                comments.clear();
                loadComments(url);
            }
        });

        return view;
    }

    public void loadComments(String url) {
        this.url = url;
        ApiComments apiComments = Adapter.restAdapter.create(ApiComments.class);
        apiComments.getComments(url, currentPage, new Callback<Comments>() {
            @Override
            public void success(Comments newComments, Response response) {
                int commentsPerPage = 50;
                if (previousCount < commentsPerPage) {
                    // Add new comments to existing "page"
                    comments.subList(comments.size() - previousCount, comments.size()).clear();
                    comments.addAll(newComments.comments);
                } else {
                    // Show new comments "page"
                    comments.addAll(newComments.comments);
                }

                // If loaded all comments at once, increment currentPage
                if (newComments.comments.size() == commentsPerPage) {
                    currentPage++;
                }

                previousCount = newComments.comments.size();
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
}
