package dev.trubitsyn.lorforandroid.ui.section.forum

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dev.trubitsyn.lorforandroid.ui.section.forum.section.ForumSectionItem

class ForumOverviewViewModel : ViewModel() {
    private val itemFactory = ForumOverviewItemFactory()
    private val path = "forum"

    val forumSections: LiveData<List<ForumSectionItem>> = TODO()
}