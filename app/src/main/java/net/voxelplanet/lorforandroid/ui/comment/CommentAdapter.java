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

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.voxelplanet.lorforandroid.R;
import net.voxelplanet.lorforandroid.model.Comment;
import net.voxelplanet.lorforandroid.util.CommentUtils;
import net.voxelplanet.lorforandroid.util.StringUtils;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    private final List<Comment> comments;
    private final Context context;
    private final CommentClickListener listener;

    public CommentAdapter(List<Comment> comments, Context context, CommentClickListener listener) {
        this.comments = comments;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(final ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_comment, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CommentAdapter.ViewHolder viewHolder, final int position) {
        Comment comment = comments.get(position);
        viewHolder.author.setText(comment.getAuthor().getNick());
        //viewHolder.avatar.setImageURI(Uri.parse(comment.getAvatar()));
        viewHolder.message.setText(StringUtils.removeLineBreak(Html.fromHtml(comment.getProcessedMessage())));
        viewHolder.stars.setText(comment.getAuthor().getStars());
        viewHolder.date.setText(StringUtils.getDate(comment.getPostdate()));
        if (CommentUtils.isReply(comment)) {
            final Comment parent = CommentUtils.getParent(comments, comment);
            viewHolder.replyTo.setText(context.getString(R.string.replyTo) + " " + parent.getAuthor().getNick());
            viewHolder.replyTo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onParentLink(parent);
                }
            });
        } else viewHolder.replyTo.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView author;
        //private ImageView avatar;
        private final TextView message;
        private final TextView stars;
        private final TextView date;
        private final TextView replyTo;

        public ViewHolder(View commentView) {
            super(commentView);
            replyTo = (TextView) commentView.findViewById(R.id.commentReplyTo);
            author = (TextView) commentView.findViewById(R.id.commentAuthor);
            //avatar = (ImageView) commentView.findViewById(R.id.commentAvatar);
            message = (TextView) commentView.findViewById(R.id.commentMessage);
            stars = (TextView) commentView.findViewById(R.id.commentStars);
            date = (TextView) commentView.findViewById(R.id.commentDate);
        }
    }
}