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

package io.github.getsmp.lorforandroid.ui.comment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.List;

import io.github.getsmp.lorforandroid.R;
import io.github.getsmp.lorforandroid.api.ApiComments;
import io.github.getsmp.lorforandroid.api.ApiManager;
import io.github.getsmp.lorforandroid.model.Comment;
import io.github.getsmp.lorforandroid.model.Comments;
import io.github.getsmp.lorforandroid.ui.base.BaseListFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentFragment extends BaseListFragment {
    private int page, previousCount = 0;
    private String url;

    public static CommentFragment newInstance(String url) {
        CommentFragment commentFragment = new CommentFragment();
        Bundle args = new Bundle();
        args.putString("url", url);
        commentFragment.setArguments(args);
        return commentFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url = getArguments().getString("url");
    }

    @Override
    protected void getListItems() {
        Call<Comments> comments = ApiManager.INSTANCE.apiRestAdapter.create(ApiComments.class).getComments(url, page);
        comments.enqueue(new Callback<Comments>() {
            @Override
            public void onResponse(Call<Comments> call, Response<Comments> response) {
                if (response.body() != null) {
                    if (response.body().comments.size() > 0) {
                        List<Comment> comments = response.body().comments;
                        int commentsPerPage = 50;

                        if (previousCount < commentsPerPage) {
                            // Add new comments to existing "page"
                            int itemSize = items.size();
                            items.subList(itemSize - previousCount, itemSize).clear();
                            items.addAll(comments);
                        } else {
                            // Show new comments "page"
                            items.addAll(comments);
                        }

                        int commentSize = comments.size();

                        // If loaded all comments at once, increment currentPage
                        if (commentSize == commentsPerPage) {
                            page++;
                        }

                        previousCount = commentSize;
                        adapter.notifyDataSetChanged();
                    } else if (response.body().comments.size() == 0) {
                        if (previousCount == 0) {
                            showErrorView(R.string.error_no_comments);
                        } else if (previousCount > 0) {
                            Toast.makeText(context, R.string.error_no_comments, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                stopRefreshAndShow();
            }

            @Override
            public void onFailure(Call<Comments> call, Throwable t) {
                if (previousCount > 0) {
                    Toast.makeText(context, R.string.error_network, Toast.LENGTH_SHORT).show();
                } else showErrorView(R.string.error_network);
            }
        });
    }

    @Override
    protected void clearData() {
        page = 0;
        previousCount = 0;
        items.clear();
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        return new CommentAdapter(items, context);
    }
}
