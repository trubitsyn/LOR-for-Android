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

package io.github.getsmp.lorforandroid.ui.section.news;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.github.getsmp.lorforandroid.R;

public class NewsViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.newsTitle) TextView title;
    @Bind(R.id.newsCategory) TextView category;
    @Bind(R.id.newsTags) TextView tags;
    @Bind(R.id.newsAuthor) TextView author;
    @Bind(R.id.newsDate) TextView date;
    @Bind(R.id.newsCommentsCount) TextView commentsCount;

    public NewsViewHolder(View itemView) {
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
