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

package dev.trubitsyn.lorforandroid.ui.topic

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import dev.trubitsyn.lorforandroid.R
import dev.trubitsyn.lorforandroid.databinding.TopicFragmentBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TopicFragment : Fragment() {
    private val args by navArgs<TopicFragmentArgs>()
    private val viewModel by viewModels<TopicViewModel> {
        TopicViewModelFactory(requireContext(), args.url)
    }
    private lateinit var binding: TopicFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_topic, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                findNavController().navigateUp()
                true
            }
            R.id.showComments -> {
                val action = TopicFragmentDirections.actionTopicToComment(args.url)
                findNavController().navigate(action)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = TopicFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.topicMessage.movementMethod = LinkMovementMethod.getInstance()
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.flow.collectLatest {
                binding.topic = it
                binding.executePendingBindings()
            }
        }
    }

    //    override fun onItemClickCallback(position: Int) {
//        var item: TrackerItem by Delegates.notNull() //items[position] as TrackerItem
//        item.let {
//            if (GalleryUtils.isGalleryUrl(it.url)) {
//                navigateToGalleryTopic(it)
//            } else {
//                navigateToTopic(it)
//            }
//        }
//
//    }

//    private fun navigateToGalleryTopic(item: TrackerItem) {
//        val imagesUrl = GalleryUtils.getGalleryImagesUrl("https://linux.org.ru/", item.url)
//        val medium2xImageUrl = GalleryUtils.getMedium2xImageUrl(imagesUrl)
//        val mediumImageUrl = GalleryUtils.getMediumImageUrl(imagesUrl)
//
//        // TODO: Url of high-res image in GalleryItem
//        // Currently cannot get it because images can either have .jpg or .png extension
//        // and there's no way to determine the correct without issuing an HTTP request.
//        val galleryItem = GalleryItem(
//                url = item.url,
//                title = item.title,
//                groupTitle = item.groupTitle,
//                date = item.date,
//                tags = item.tags,
//                author = item.author!!,
//                comments = item.comments,
//                imageUrl = imagesUrl,
//                medium2xImageUrl = medium2xImageUrl,
//                mediumImageUrl = mediumImageUrl)
//        val action = TrackerSectionFragmentDirections.actionTrackerToTopic(
//                url = galleryItem.url,
//                imageUrl = galleryItem.imageUrl
//        )
//        findNavController().navigate(action)
//    }
}
