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
