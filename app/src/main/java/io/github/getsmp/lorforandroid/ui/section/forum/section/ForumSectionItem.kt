package io.github.getsmp.lorforandroid.ui.section.forum.section

import io.github.getsmp.lorforandroid.ui.section.Item

class ForumSectionItem(url: String, title: String, groupTitle: String?, tags: String, date: String, author: String?, comments: String, val isPinned: Boolean) : Item(url, title, groupTitle, tags, date, author, comments) {
}