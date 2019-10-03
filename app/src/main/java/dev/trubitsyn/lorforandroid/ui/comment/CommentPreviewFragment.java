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

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dev.trubitsyn.lorforandroid.R;
import dev.trubitsyn.lorforandroid.api.model.Comment;

public class CommentPreviewFragment extends DialogFragment {
    @BindView(R.id.commentReplyTo) TextView reply;
    @BindView(R.id.commentMessage) TextView message;
    @BindView(R.id.commentAuthor) TextView author;
    @BindView(R.id.commentStars) TextView stars;
    @BindView(R.id.commentDate) TextView date;
    private Unbinder unbinder;
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
        unbinder = ButterKnife.bind(this, view);
        CommentUtils.initView(comments, comment, activity, reply, message, author, stars, date);
        AlertDialog.Builder builder = new AlertDialog.Builder(activity).setView(view);
        return builder.create();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
