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
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.loopj.android.http.RequestParams
import dev.trubitsyn.lorforandroid.R
import dev.trubitsyn.lorforandroid.ui.section.SectionFragment
import dev.trubitsyn.lorforandroid.ui.section.gallery.GalleryItem
import dev.trubitsyn.lorforandroid.ui.section.gallery.GalleryUtils
import dev.trubitsyn.lorforandroid.ui.util.SpinnerViewUtils

class TrackerFragment : SectionFragment() {
    private val args by navArgs<TrackerFragmentArgs>()
    private var filter: Int = 0

    override val itemsPerPage = 30

    override val path = "tracker"

    override val requestParams: RequestParams
        get() = RequestParams("offset", offset, "filter", TrackerFilterEnum.values()[filter].name)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        filter = args.filter.ordinal
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

    override val itemFactory
        get() = TrackerItemFactory(context!!)

    override val maxOffset = 180

    override val adapter: TrackerAdapter
        get() = TrackerAdapter(items as MutableList<TrackerItem>)

    override fun onItemClickCallback(position: Int) {
        val item = items[position] as TrackerItem
        if (GalleryUtils.isGalleryUrl(item.url)) {
            val imagesUrl = GalleryUtils.getGalleryImagesUrl(item.url)
            val medium2xImageUrl = GalleryUtils.getMedium2xImageUrl(imagesUrl)
            val mediumImageUrl = GalleryUtils.getMediumImageUrl(imagesUrl)

            // TODO: Url of high-res image in GalleryItem
            // Currently cannot get it because images can either have .png extension or .jpg and there's no way to determine the correct without issuing a HTTP request.
            val galleryItem = GalleryItem(item.url, item.title, item.groupTitle, item.date, item.tags, item.author!!, item.comments, medium2xImageUrl, medium2xImageUrl, mediumImageUrl)
            val action = TrackerFragmentDirections.actionTrackerToTopic(
                    url = galleryItem.url,
                    imageUrl = galleryItem.imageUrl
            )
            findNavController().navigate(action)
        } else {
            val action = TrackerFragmentDirections.actionTrackerToTopic(
                    url = item.url,
                    imageUrl = null
            )
            findNavController().navigate(action)
        }
    }

    companion object {
        const val TAG = "trackerFragment"
    }
}
