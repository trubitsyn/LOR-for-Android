/*
 * Copyright 2016 getsmp
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

package io.github.getsmp.lorforandroid.ui.section.tracker;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.github.getsmp.lorforandroid.R;

public class TrackerViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.trackerTitle) TextView title;
    @Bind(R.id.trackerGroupTitle) TextView category;
    @Bind(R.id.trackerTags) TextView tags;
    @Bind(R.id.trackerAuthor) TextView author;
    @Bind(R.id.trackerDate) TextView date;
    @Bind(R.id.trackerCommentsCount) TextView commentsCount;

    public TrackerViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
