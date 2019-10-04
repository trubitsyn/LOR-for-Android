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

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

import dev.trubitsyn.lorforandroid.R
import dev.trubitsyn.lorforandroid.util.PreferenceUtils

class GalleryAdapter(private val items: List<GalleryItem>, private val context: Context) : RecyclerView.Adapter<GalleryViewHolder>() {
    private val shouldLoadImages: Boolean

    init {
        shouldLoadImages = PreferenceUtils.shouldLoadImagesNow(context)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): GalleryViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_gallery, viewGroup, false)
        return GalleryViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: GalleryViewHolder, i: Int) {
        val item = items[i]
        viewHolder.title!!.text = item.title

        if (item.groupTitle == null) {
            viewHolder.category!!.visibility = View.GONE
        } else
            viewHolder.category!!.text = item.groupTitle

        if (item.tags.isEmpty()) {
            viewHolder.tags!!.visibility = View.GONE
        } else
            viewHolder.tags!!.text = item.tags

        viewHolder.date!!.text = item.date
        viewHolder.author!!.text = item.author
        viewHolder.commentsCount!!.text = item.comments

        if (shouldLoadImages) {
            Glide.with(context).load(item.mediumImageUrl).diskCacheStrategy(DiskCacheStrategy.ALL).into(viewHolder.image!!)
        } else
            viewHolder.image!!.visibility = View.GONE
    }

    override fun getItemCount(): Int {
        return items.size
    }
}