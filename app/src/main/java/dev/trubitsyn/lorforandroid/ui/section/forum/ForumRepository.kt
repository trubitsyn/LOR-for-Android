package dev.trubitsyn.lorforandroid.ui.section.forum

interface ForumRepository {
    fun getItems(): List<ForumOverviewItem>
}