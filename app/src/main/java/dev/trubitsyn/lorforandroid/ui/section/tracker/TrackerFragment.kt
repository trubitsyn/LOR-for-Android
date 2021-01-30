/*
 * Copyright (C) 2015-2021 Nikola Trubitsyn (getsmp)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package dev.trubitsyn.lorforandroid.ui.section.tracker

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.trubitsyn.lorforandroid.R
import dev.trubitsyn.lorforandroid.ui.base.BaseListFragment
import dev.trubitsyn.lorforandroid.ui.section.gallery.GalleryItem
import dev.trubitsyn.lorforandroid.ui.section.gallery.GalleryUtils
import dev.trubitsyn.lorforandroid.ui.util.SpinnerViewUtils
import kotlin.properties.Delegates

class TrackerFragment : BaseListFragment() {
    private val args by navArgs<TrackerFragmentArgs>()
    private var filter: TrackerFilterEnum = TrackerFilterEnum.all
    //private val viewModel by viewModels<TrackerViewModel> { TrackerViewModelFactory(filter) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        filter = args.filter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        SpinnerViewUtils.setSpinnerView(requireActivity(), R.array.tracker_spinner, filter.ordinal, object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                filter = TrackerFilterEnum.values()[position]
                //restart()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        })
        //viewModel.trackerItems.observe(viewLifecycleOwner, {
            //stopRefreshAndShow()
            //adapter.submitList(it)
        //})
    }

    override val adapter: PagingDataAdapter<*, RecyclerView.ViewHolder>
        get() = TODO()

    override fun onItemClickCallback(position: Int) {
        var item: TrackerItem by Delegates.notNull() //items[position] as TrackerItem
        item.let {
            if (GalleryUtils.isGalleryUrl(it.url)) {
                navigateToGalleryTopic(it)
            } else {
                navigateToTopic(it)
            }
        }

    }

    private fun navigateToGalleryTopic(item: TrackerItem) {
        val imagesUrl = GalleryUtils.getGalleryImagesUrl("https://linux.org.ru/", item.url)
        val medium2xImageUrl = GalleryUtils.getMedium2xImageUrl(imagesUrl)
        val mediumImageUrl = GalleryUtils.getMediumImageUrl(imagesUrl)

        // TODO: Url of high-res image in GalleryItem
        // Currently cannot get it because images can either have .jpg or .png extension
        // and there's no way to determine the correct without issuing an HTTP request.
        val galleryItem = GalleryItem(
                url = item.url,
                title = item.title,
                groupTitle = item.groupTitle,
                date = item.date,
                tags = item.tags,
                author = item.author!!,
                comments = item.comments,
                imageUrl = imagesUrl,
                medium2xImageUrl = medium2xImageUrl,
                mediumImageUrl = mediumImageUrl)
        val action = TrackerFragmentDirections.actionTrackerToTopic(
                url = galleryItem.url,
                imageUrl = galleryItem.imageUrl
        )
        findNavController().navigate(action)
    }

    private fun navigateToTopic(item: TrackerItem) {
        val action = TrackerFragmentDirections.actionTrackerToTopic(
                url = item.url,
                imageUrl = null
        )
        findNavController().navigate(action)
    }
}
