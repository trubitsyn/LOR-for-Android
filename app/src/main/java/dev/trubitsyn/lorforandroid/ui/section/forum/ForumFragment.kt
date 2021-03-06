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

package dev.trubitsyn.lorforandroid.ui.section.forum

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.trubitsyn.lorforandroid.R
import dev.trubitsyn.lorforandroid.ui.base.BaseListFragment
import dev.trubitsyn.lorforandroid.util.StringUtils
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ForumFragment : BaseListFragment() {

    override lateinit var adapter: ForumAdapter
    private val viewModel: ForumViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
        adapter = ForumAdapter(viewModel)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        swipeRefreshLayout?.isEnabled = false
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.selectionState.collectLatest { state ->
                when (state) {
                    is ForumViewModel.SelectionState.Item -> {
                        onItemSelected(state.item)
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

    private fun onItemSelected(item: ForumItem) {
        if (StringUtils.isClub(item.url)) {
            Toast.makeText(context, R.string.error_access_denied, Toast.LENGTH_SHORT).show()
        } else {
            val action = ForumFragmentDirections.actionForumToForumSection(
                    group = item.url,
                    name = item.name
            )
            findNavController().navigate(action)
        }
    }

    override val showDividers = false
}
