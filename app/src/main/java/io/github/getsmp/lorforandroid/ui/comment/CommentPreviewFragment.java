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

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.github.getsmp.lorforandroid.R;
import io.github.getsmp.lorforandroid.model.Comment;

public class CommentPreviewFragment extends DialogFragment {
    @Bind(R.id.commentReplyTo) TextView reply;
    @Bind(R.id.commentMessage) TextView message;
    @Bind(R.id.commentAuthor) TextView author;
    @Bind(R.id.commentStars) TextView stars;
    @Bind(R.id.commentDate) TextView date;
    private Activity activity;
    private Comment comment;
    private List<Comment> comments;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public void setComments(List<Comment> comments, Comment comment) {
        this.comments = comments;
        this.comment = comment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = View.inflate(activity, R.layout.comment_preview, null);
        ButterKnife.bind(this, view);
        CommentUtils.initView(comments, comment, activity, reply, message, author, stars, date);
        AlertDialog.Builder builder = new AlertDialog.Builder(activity).setView(view);
        return builder.create();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
