package io.github.getsmp.lorforandroid.ui.section.forum.section;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.github.getsmp.lorforandroid.R;

public class ForumSectionViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.forumTitle) TextView title;
    @Bind(R.id.forumTags) TextView tags;
    @Bind(R.id.forumReplyFrom) TextView replyFrom;
    @Bind(R.id.forumReplyDate) TextView replyDate;
    @Bind(R.id.forumCommentsCount) TextView commentsCount;

    public ForumSectionViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
