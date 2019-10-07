package dev.trubitsyn.lorforandroid.ui.section.forum.section

interface ForumSectionRepository {
    fun getItems(): List<ForumSectionItem>
}