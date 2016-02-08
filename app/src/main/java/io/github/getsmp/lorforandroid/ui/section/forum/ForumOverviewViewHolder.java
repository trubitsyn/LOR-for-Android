package io.github.getsmp.lorforandroid.ui.section.forum;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.github.getsmp.lorforandroid.R;


public class ForumOverviewViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.itemName) TextView itemName;

    public ForumOverviewViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
