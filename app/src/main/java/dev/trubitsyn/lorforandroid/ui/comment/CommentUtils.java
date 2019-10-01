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

package dev.trubitsyn.lorforandroid.ui.comment;

import android.content.Context;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import dev.trubitsyn.lorforandroid.R;
import dev.trubitsyn.lorforandroid.api.model.Comment;
import dev.trubitsyn.lorforandroid.util.DateUtils;
import dev.trubitsyn.lorforandroid.util.StringUtils;

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
