/*
 * Copyright (C) 2015-2019 Nikola Trubitsyn
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
import androidx.navigation.fragment.navArgs
import dev.trubitsyn.lorforandroid.R
import dev.trubitsyn.lorforandroid.ui.base.BaseListFragment
import dev.trubitsyn.lorforandroid.ui.util.SpinnerViewUtils

class GalleryFragment : BaseListFragment() {
    private val args by navArgs<GalleryFragmentArgs>()
    private var filter: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        filter = args.filter.ordinal
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

    override val adapter
        get() = TODO()

    override fun onItemClickCallback(position: Int) {
        /*val item = items[position] as GalleryItem
        val action = GalleryFragmentDirections.actionGalleryToTopic(
                url = item.url,
                imageUrl = item.imageUrl
        )
        findNavController().navigate(action)*/
    }
}
