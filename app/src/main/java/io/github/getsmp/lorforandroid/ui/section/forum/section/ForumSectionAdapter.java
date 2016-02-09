package io.github.getsmp.lorforandroid.ui.section.forum.section;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import io.github.getsmp.lorforandroid.R;

public class ForumSectionAdapter extends RecyclerView.Adapter<ForumSectionViewHolder> {
    private List<ForumSectionItem> items;

    public ForumSectionAdapter(List<ForumSectionItem> items) {
        this.items = items;
    }

    @Override
    public ForumSectionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_forum, parent, false);
        return new ForumSectionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ForumSectionViewHolder v, int position) {
        ForumSectionItem item = items.get(position);
        v.title.setText(item.getTitle());
        if (item.getTags().length() > 0 || item.getTags() == null) {
            v.tags.setText(item.getTags());
        } else v.tags.setVisibility(View.GONE);
        v.replyFrom.setText(item.getReplyFrom());
        v.replyDate.setText(item.getReplyDate());
        v.commentsCount.setText(item.getCommentsCount());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
