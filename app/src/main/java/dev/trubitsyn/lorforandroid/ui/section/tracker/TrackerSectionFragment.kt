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
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.trubitsyn.lorforandroid.ui.base.BaseListFragment
import dev.trubitsyn.lorforandroid.ui.base.SelectionState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TrackerSectionFragment : BaseListFragment() {

    override lateinit var adapter: TrackerAdapter
    private lateinit var args: TrackerSectionFragmentArgs
    private val viewModel by viewModels<TrackerViewModel> {
        TrackerViewModelFactory(requireContext().applicationContext, args.filter)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        args = TrackerSectionFragmentArgs.fromBundle(requireArguments())
        adapter = TrackerAdapter(viewModel)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.selectionState.collectLatest { state ->
                when (state) {
                    is SelectionState.Item -> {
                        onItemSelected(state.item as TrackerItem)
                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.flow.collectLatest {
                adapter.submitData(it)
            }
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun onItemSelected(item: TrackerItem) {
        val action = TrackerFragmentDirections.actionTrackerToTopic(
                url = item.url,
                imageUrl = null
        )
        findNavController().navigate(action)
    }

    companion object {

        fun newInstance(@TrackerFilter filter: String): TrackerSectionFragment {
            return TrackerSectionFragment().apply {
                arguments = TrackerSectionFragmentArgs(filter).toBundle()
            }
        }
    }
}
