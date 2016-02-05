package io.github.getsmp.lorforandroid.ui.section.tracker;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.github.getsmp.lorforandroid.R;

public class TrackerViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.trackerTitle) TextView title;
    @Bind(R.id.trackerGroupTitle) TextView category;
    @Bind(R.id.trackerTags) TextView tags;
    @Bind(R.id.trackerAuthor) TextView author;
    @Bind(R.id.trackerDate) TextView date;
    @Bind(R.id.trackerCommentsCount) TextView commentsCount;

    public TrackerViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public TextView getTitle() {
        return title;
    }

    public TextView getCategory() {
        return category;
    }

    public TextView getTags() {
        return tags;
    }

    public TextView getAuthor() {
        return author;
    }

    public TextView getDate() {
        return date;
    }

    public TextView getCommentsCount() {
        return commentsCount;
    }
}
