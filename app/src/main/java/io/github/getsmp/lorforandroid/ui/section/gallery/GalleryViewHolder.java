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

package io.github.getsmp.lorforandroid.ui.section.gallery;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.github.getsmp.lorforandroid.R;

public class GalleryViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.galleryTitleAuthor) TextView title;
    @Bind(R.id.galleryImage) ImageView image;
    @Bind(R.id.galleryTags) TextView tags;
    @Bind(R.id.galleryCommentsCount) TextView commentsCount;

    public GalleryViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public TextView getTitle() {
        return title;
    }

    public ImageView getImage() {
        return image;
    }

    public TextView getTags() {
        return tags;
    }

    public TextView getCommentsCount() {
        return commentsCount;
    }
}
