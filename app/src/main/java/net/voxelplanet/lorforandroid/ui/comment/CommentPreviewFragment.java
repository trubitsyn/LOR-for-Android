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
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import net.voxelplanet.lorforandroid.R;
import net.voxelplanet.lorforandroid.model.Comment;
import net.voxelplanet.lorforandroid.util.CommentUtils;
import net.voxelplanet.lorforandroid.util.StringUtils;

import java.util.List;

public class CommentPreviewFragment extends DialogFragment {
    private Activity activity;
    private Comment comment;
    private CommentClickListener listener;
    private List<Comment> comments;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
        this.listener = (CommentClickListener) activity;
    }

    public void initializeView(List<Comment> comments, Comment comment) {
        this.comments = comments;
        this.comment = comment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.comment_preview, null);
        TextView commentReply = (TextView) view.findViewById(R.id.commentReplyTo);
        TextView commentMessage = (TextView) view.findViewById(R.id.commentMessage);
        TextView commentAuthor = (TextView) view.findViewById(R.id.commentAuthor);
        TextView commentStars = (TextView) view.findViewById(R.id.commentStars);
        TextView commentDate = (TextView) view.findViewById(R.id.commentDate);

        if (comment.getReply() != null) {
            final Comment parent = CommentUtils.getParent(comments, comment.getReply().getId());
            commentReply.setText(activity.getString(R.string.replyTo) + " " + parent.getAuthor().getNick());
            commentReply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onParentLink(comments, parent);
                }
            });
        } else commentReply.setVisibility(View.GONE);
        commentMessage.setText(StringUtils.removeLineBreak(Html.fromHtml(comment.getProcessedMessage())));
        commentMessage.setMovementMethod(LinkMovementMethod.getInstance());
        commentAuthor.setText(comment.getAuthor().getNick());
        commentStars.setText(comment.getAuthor().getStars());
        commentDate.setText(StringUtils.getDate(comment.getPostdate()));

        AlertDialog.Builder builder = new AlertDialog.Builder(activity).setView(view);
        return builder.create();
    }
}
