package dev.trubitsyn.lorforandroid.ui.section.news

interface NewsRepository {
    fun getItems(offset: Int): List<NewsItem>
}