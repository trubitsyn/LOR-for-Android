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

import android.os.Bundle
import android.view.View
import android.widget.AdapterView

import androidx.recyclerview.widget.RecyclerView

import com.loopj.android.http.RequestParams

import dev.trubitsyn.lorforandroid.R
import dev.trubitsyn.lorforandroid.ui.section.ItemFactory
import dev.trubitsyn.lorforandroid.ui.section.SectionFragment
import dev.trubitsyn.lorforandroid.ui.util.ItemClickCallback
import dev.trubitsyn.lorforandroid.ui.util.SpinnerViewUtils

class GalleryFragment : SectionFragment() {
    private var filter: Int = 0

    private val isAll: Boolean
        get() = filter == GalleryFilterEnum.all.ordinal

    override val itemsPerPage: Int
        get() = 20

    override val path: String
        get() {
            val path = if (isAll) "" else "/" + GalleryFilterEnum.values()[filter].name
            return "gallery$path"
        }

    override val requestParams: RequestParams
        get() = RequestParams("offset", offset)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        filter = arguments!!.getInt("filter")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        SpinnerViewUtils.setSpinnerView(activity!!, R.array.gallery_spinner, filter, object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                filter = position
                restart()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        })
    }

    override fun getItemFactory(): ItemFactory {
        return GalleryItemFactory()
    }

    override fun getMaxOffset(): Int {
        return 200
    }

    override fun getAdapter_(): RecyclerView.Adapter<*> {
        return GalleryAdapter(items as MutableList<GalleryItem>, context_)
    }

    override fun onItemClickCallback(position: Int) {
        val item = items[position] as GalleryItem
        (context_ as ItemClickCallback).onGalleryTopicRequested(item)
    }

    companion object {

        fun newInstance(galleryFilterEnum: GalleryFilterEnum): GalleryFragment {
            val galleryFragment = GalleryFragment()
            val args = Bundle()
            args.putInt("filter", galleryFilterEnum.ordinal)
            galleryFragment.arguments = args
            return galleryFragment
        }
    }
}
