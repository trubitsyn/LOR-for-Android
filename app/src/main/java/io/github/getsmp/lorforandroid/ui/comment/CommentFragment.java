/*
 * Copyright (c) 2016 getsmp
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

package io.github.getsmp.lorforandroid.ui.comment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import io.github.getsmp.lorforandroid.R;
import io.github.getsmp.lorforandroid.api.ApiManager;
import io.github.getsmp.lorforandroid.api.model.Comment;
import io.github.getsmp.lorforandroid.api.model.Comments;
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
    protected void fetchData() {
        Call<Comments> comments = ApiManager.INSTANCE.getApiComments().getComments(url, page);
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
                        showUserFriendlyError(R.string.error_no_comments);
                    }
                }
                stopRefreshAndShow();
            }

            @Override
            public void onFailure(Call<Comments> call, Throwable t) {
                showUserFriendlyError(R.string.error_network);
            }
        });
    }

    @Override
    protected void clearData() {
        super.clearData();
        page = 0;
        previousCount = 0;
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        return new CommentAdapter(items, context);
    }
}
