package io.github.getsmp.lorforandroid.ui.section.forum;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.github.getsmp.lorforandroid.R;

public class ForumViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.forumTitle) TextView title;
    @Bind(R.id.forumTags) TextView tags;
    @Bind(R.id.forumReplyFrom) TextView replyFrom;
    @Bind(R.id.forumReplyDate) TextView replyDate;
    @Bind(R.id.forumCommentsCount) TextView commentsCount;

    public ForumViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
