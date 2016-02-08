package io.github.getsmp.lorforandroid.ui.section.forum.section;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import io.github.getsmp.lorforandroid.R;


public class ForumSectionAdapter extends RecyclerView.Adapter<ForumSectionViewHolder> {
    private List<ForumSection> sections;

    public ForumSectionAdapter(List<ForumSection> sections) {
        this.sections = sections;
    }

    @Override
    public ForumSectionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_simple, parent, false);
        return new ForumSectionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ForumSectionViewHolder v, int position) {
        v.itemName.setText(sections.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return sections.size();
    }
}
