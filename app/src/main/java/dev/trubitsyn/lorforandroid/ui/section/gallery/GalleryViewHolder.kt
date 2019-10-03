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

package dev.trubitsyn.lorforandroid.ui.section.gallery

import android.view.View
import android.widget.ImageView
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView

import butterknife.BindView
import butterknife.ButterKnife
import dev.trubitsyn.lorforandroid.R

class GalleryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    @BindView(R.id.galleryTitle)
    internal var title: TextView? = null
    @BindView(R.id.galleryCategory)
    internal var category: TextView? = null
    @BindView(R.id.galleryTags)
    internal var tags: TextView? = null
    @BindView(R.id.galleryAuthor)
    internal var author: TextView? = null
    @BindView(R.id.galleryDate)
    internal var date: TextView? = null
    @BindView(R.id.galleryImage)
    internal var image: ImageView? = null
    @BindView(R.id.galleryCommentsCount)
    internal var commentsCount: TextView? = null

    init {
        ButterKnife.bind(this, itemView)
    }
}
