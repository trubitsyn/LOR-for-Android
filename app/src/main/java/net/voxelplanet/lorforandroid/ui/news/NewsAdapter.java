/*
 * Copyright 2015 getsmp
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.voxelplanet.lorforandroid.ui.news;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.voxelplanet.lorforandroid.R;
import net.voxelplanet.lorforandroid.model.Topic;
import net.voxelplanet.lorforandroid.util.StringUtils;

import java.util.Date;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsViewHolder> {
    private final List<Topic> newsItems;

    public NewsAdapter(List<Topic> topics) {
        this.newsItems = topics;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(final ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_news, viewGroup, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsViewHolder viewHolder, int i) {
        Topic topic = newsItems.get(i);
        initView(viewHolder, topic.getTitle(), "", topic.getTags(), topic.getAuthor().getNick(), topic.getPostDate(), topic.getCommentsCount());
    }

    public static void initView(NewsViewHolder viewHolder, String title, String groupTitle, List<String> tags, String nick, Date date, int commentsCount) {
        viewHolder.getTitle().setText(Html.fromHtml(title));
        viewHolder.getCategory().setText(groupTitle);

        if (tags.size() == 0) {
            viewHolder.getTags().setVisibility(View.GONE);
        } else viewHolder.getTags().setText(TextUtils.join(", ", tags));

        viewHolder.getAuthor().setText(nick);
        viewHolder.getDate().setText(StringUtils.getDate(date));
        viewHolder.getCommentsCount().setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return newsItems.size();
    }
}
