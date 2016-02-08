package io.github.getsmp.lorforandroid.ui.section.forum;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import io.github.getsmp.lorforandroid.R;

public class ForumAdapter extends RecyclerView.Adapter<ForumViewHolder> {
    private List<ForumItem> items;

    public ForumAdapter(List<ForumItem> items) {
        this.items = items;
    }

    @Override
    public ForumViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_forum, parent, false);
        return new ForumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ForumViewHolder v, int position) {
        ForumItem item = items.get(position);
        v.title.setText(item.getTitle());
        v.tags.setText(item.getTags());
        v.replyFrom.setText(item.getReplyFrom());
        v.replyDate.setText(item.getReplyDate());
        v.commentsCount.setText(item.getCommentsCount());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
