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
import android.view.View;
import android.widget.TextView;

import net.voxelplanet.lorforandroid.R;
import net.voxelplanet.lorforandroid.model.Comment;

import java.util.List;

public class CommentPreviewFragment extends DialogFragment {
    private Activity activity;
    private Comment comment;
    private List<Comment> comments;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    public void setComments(List<Comment> comments, Comment comment) {
        this.comments = comments;
        this.comment = comment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = View.inflate(activity, R.layout.comment_preview, null);
        TextView reply = (TextView) view.findViewById(R.id.commentReplyTo);
        TextView message = (TextView) view.findViewById(R.id.commentMessage);
        TextView author = (TextView) view.findViewById(R.id.commentAuthor);
        TextView stars = (TextView) view.findViewById(R.id.commentStars);
        TextView date = (TextView) view.findViewById(R.id.commentDate);

        CommentUtils.initView(comments, comment, activity, reply, message, author, stars, date);

        AlertDialog.Builder builder = new AlertDialog.Builder(activity).setView(view);
        return builder.create();
    }
}
