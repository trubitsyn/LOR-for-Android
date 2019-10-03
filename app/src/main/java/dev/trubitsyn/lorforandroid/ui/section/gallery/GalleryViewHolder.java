/*
 * Copyright (C) 2015-2016 Nikola Trubitsyn
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package dev.trubitsyn.lorforandroid.ui.section.gallery;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import dev.trubitsyn.lorforandroid.R;

public class GalleryViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.galleryTitle) TextView title;
    @Bind(R.id.galleryCategory) TextView category;
    @Bind(R.id.galleryTags) TextView tags;
    @Bind(R.id.galleryAuthor) TextView author;
    @Bind(R.id.galleryDate) TextView date;
    @Bind(R.id.galleryImage) ImageView image;
    @Bind(R.id.galleryCommentsCount) TextView commentsCount;

    public GalleryViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}