package io.github.getsmp.lorforandroid.ui.section.forum.section;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import io.github.getsmp.lorforandroid.R;
import io.github.getsmp.lorforandroid.ui.section.ItemCommon;

public class ForumSectionAdapter extends RecyclerView.Adapter<ForumSectionViewHolder> {
    private List<ItemCommon> items;

    public ForumSectionAdapter(List<ItemCommon> items) {
        this.items = items;
    }

    @Override
    public ForumSectionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_forum, parent, false);
        return new ForumSectionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ForumSectionViewHolder v, int position) {
        ItemCommon item = items.get(position);
        v.title.setText(item.getTitle());
        if (item.getTags().length() > 0 || item.getTags() == null) {
            v.tags.setText(item.getTags());
        } else v.tags.setVisibility(View.GONE);
        v.replyFrom.setText(item.getAuthor());
        v.replyDate.setText(item.getDate());
        v.commentsCount.setText(item.getComments());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
