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
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.voxelplanet.lorforandroid.R;
import net.voxelplanet.lorforandroid.model.Comment;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    private final List<Comment> comments;
    private final Activity activity;

    public CommentAdapter(List<Comment> comments, Activity activity) {
        this.comments = comments;
        this.activity = activity;
    }

    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(final ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_comment, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CommentAdapter.ViewHolder v, final int position) {
        Comment comment = comments.get(position);
        CommentUtils.initView(comments, comment, activity, v.replyTo, v.message, v.author, v.stars, v.date);
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.commentAuthor) private TextView author;
        @Bind(R.id.commentMessage) private TextView message;
        @Bind(R.id.commentStars) private TextView stars;
        @Bind(R.id.commentDate) private TextView date;
        @Bind(R.id.commentReplyTo) private TextView replyTo;

        public ViewHolder(View commentView) {
            super(commentView);
            ButterKnife.bind(this, commentView);
        }
    }
}