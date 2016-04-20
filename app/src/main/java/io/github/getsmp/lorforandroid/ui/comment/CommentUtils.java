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

import android.content.Context;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import io.github.getsmp.lorforandroid.R;
import io.github.getsmp.lorforandroid.api.model.Comment;
import io.github.getsmp.lorforandroid.util.DateUtils;
import io.github.getsmp.lorforandroid.util.StringUtils;

class CommentUtils {
    public static void initView(final List<Comment> comments, Comment comment, final Context context, TextView reply, TextView message, TextView author, TextView stars, TextView date) {
        author.setText(comment.getAuthor().getNick());
        message.setText(StringUtils.removeLineBreak(Html.fromHtml(comment.getProcessedMessage())));
        message.setMovementMethod(LinkMovementMethod.getInstance());
        stars.setText(comment.getAuthor().getStars());
        date.setText(DateUtils.getDate(comment.getPostdate()));
        if (comment.getReply() != null) {
            final Comment parent = getParent(comments, comment.getReply().getId());
            reply.setText(context.getString(R.string.replyTo, parent.getAuthor().getNick()));
            reply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((CommentClickListener) context).showParent(comments, parent);
                }
            });
        } else reply.setVisibility(View.GONE);
    }

    private static Comment getParent(List<Comment> comments, int reply) {
        for (Comment comment : comments) {
            if (comment.getId().equals(reply)) {
                return comment;
            }
        }
        return null;
    }
}
