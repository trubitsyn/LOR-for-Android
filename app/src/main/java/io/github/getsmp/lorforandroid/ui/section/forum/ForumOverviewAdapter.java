package io.github.getsmp.lorforandroid.ui.section.forum;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import io.github.getsmp.lorforandroid.R;


public class ForumOverviewAdapter extends RecyclerView.Adapter<ForumOverviewViewHolder> {
    private List<ForumOverviewItem> sections;

    public ForumOverviewAdapter(List<ForumOverviewItem> sections) {
        this.sections = sections;
    }

    @Override
    public ForumOverviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_simple, parent, false);
        return new ForumOverviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ForumOverviewViewHolder v, int position) {
        v.itemName.setText(sections.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return sections.size();
    }
}
