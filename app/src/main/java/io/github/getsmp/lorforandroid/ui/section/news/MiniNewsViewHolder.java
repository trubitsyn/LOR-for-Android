package io.github.getsmp.lorforandroid.ui.section.news;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.github.getsmp.lorforandroid.R;


public class MiniNewsViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.miniNewsTitle) TextView title;
    @Bind(R.id.miniNewsCommentsCount) TextView commentsCount;

    public MiniNewsViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public TextView getTitle() {
        return title;
    }

    public TextView getCommentsCount() {
        return commentsCount;
    }
}
