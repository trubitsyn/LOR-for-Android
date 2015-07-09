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
import android.view.View;
import android.widget.TextView;

import net.voxelplanet.lorforandroid.R;

public class NewsViewHolder extends RecyclerView.ViewHolder {
    private final TextView title;
    private final TextView category;
    private final TextView tags;
    private final TextView author;
    private final TextView date;
    private final TextView commentsCount;

    public NewsViewHolder(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.newsTitle);
        category = (TextView) itemView.findViewById(R.id.newsCategory);
        tags = (TextView) itemView.findViewById(R.id.newsTags);
        author = (TextView) itemView.findViewById(R.id.newsAuthor);
        date = (TextView) itemView.findViewById(R.id.newsDate);
        commentsCount = (TextView) itemView.findViewById(R.id.newsCommentsCount);
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
