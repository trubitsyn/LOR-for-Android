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

package dev.trubitsyn.lorforandroid.ui.section.tracker

import android.os.Bundle
import android.view.View
import android.widget.AdapterView

import androidx.recyclerview.widget.RecyclerView

import com.loopj.android.http.RequestParams

import dev.trubitsyn.lorforandroid.R
import dev.trubitsyn.lorforandroid.ui.section.Item
import dev.trubitsyn.lorforandroid.ui.section.ItemFactory
import dev.trubitsyn.lorforandroid.ui.section.SectionFragment
import dev.trubitsyn.lorforandroid.ui.section.gallery.GalleryItem
import dev.trubitsyn.lorforandroid.ui.section.gallery.GalleryUtils
import dev.trubitsyn.lorforandroid.ui.util.ItemClickCallback
import dev.trubitsyn.lorforandroid.ui.util.SpinnerViewUtils

class TrackerFragment : SectionFragment() {
    private var filter: Int = 0

    override val itemsPerPage: Int
        get() = 30

    override val path: String
        get() = "tracker"

    override val requestParams: RequestParams
        get() = RequestParams("offset", offset, "filter", TrackerFilterEnum.values()[filter].name)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        filter = arguments!!.getInt(ARG_FILTER)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        SpinnerViewUtils.setSpinnerView(activity!!, R.array.tracker_spinner, filter, object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                filter = position
                restart()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        })
    }

    override fun getItemFactory(): ItemFactory {
        return TrackerItemFactory()
    }

    override fun getMaxOffset(): Int {
        return 180
    }

    override fun getAdapter_(): RecyclerView.Adapter<*> {
        return TrackerAdapter(items as MutableList<Item>)
    }

    override fun onItemClickCallback(position: Int) {
        val item = items[position] as Item
        if (GalleryUtils.isGalleryUrl(item.url)) {
            val imagesUrl = GalleryUtils.getGalleryImagesUrl(item.url)
            val medium2xImageUrl = GalleryUtils.getMedium2xImageUrl(imagesUrl)
            val mediumImageUrl = GalleryUtils.getMediumImageUrl(imagesUrl)

            // TODO: Url of high-res image in GalleryItem
            // Currently cannot get it because images can either have .png extension or .jpg and there's no way to determine the correct without issuing a HTTP request.
            val galleryItem = GalleryItem(item.url, item.title, item.groupTitle, item.date, item.tags, item.author!!, item.comments, medium2xImageUrl, medium2xImageUrl, mediumImageUrl)

            (context_ as ItemClickCallback).onGalleryTopicRequested(galleryItem)
        } else {
            (context_ as ItemClickCallback).onTopicRequested(item.url)
        }
    }

    companion object {
        const val ARG_FILTER = "filter"
        const val TAG = "trackerFragment"

        fun newInstance(trackerFilterEnum: TrackerFilterEnum): TrackerFragment {
            val trackerFragment = TrackerFragment()
            val args = Bundle()
            args.putInt(ARG_FILTER, trackerFilterEnum.ordinal)
            trackerFragment.arguments = args
            return trackerFragment
        }
    }
}
