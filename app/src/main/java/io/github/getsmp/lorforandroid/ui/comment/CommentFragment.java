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

package io.github.getsmp.lorforandroid.ui.comment;

import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.github.getsmp.lorforandroid.R;
import io.github.getsmp.lorforandroid.api.ApiManager;
import io.github.getsmp.lorforandroid.model.Comment;
import io.github.getsmp.lorforandroid.model.Comments;
import io.github.getsmp.lorforandroid.ui.base.BaseListFragment;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class CommentFragment extends BaseListFragment {
    private final List<Comment> items = new ArrayList<Comment>();
    private int page, previousCount = 0;
    private String url;

    @Override
    protected void getListItems() {
        ApiManager.API_MANAGER.getApiComments().getComments(url, page, new Callback<Comments>() {
            @Override
            public void success(Comments newComments, Response response) {
                List<Comment> comments = newComments.comments;
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
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(context, R.string.error_network, Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public void setUrl(String url) {
        this.url = url;
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
