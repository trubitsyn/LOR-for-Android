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

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.github.getsmp.lorforandroid.R;

public class CommentViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.commentAuthor) TextView author;
    @Bind(R.id.commentMessage) TextView message;
    @Bind(R.id.commentStars) TextView stars;
    @Bind(R.id.commentDate) TextView date;
    @Bind(R.id.commentReplyTo) TextView replyTo;

    public CommentViewHolder(View commentView) {
        super(commentView);
        ButterKnife.bind(this, commentView);
    }
}
