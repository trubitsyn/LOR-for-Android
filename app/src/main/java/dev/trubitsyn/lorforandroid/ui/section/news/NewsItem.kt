package dev.trubitsyn.lorforandroid.ui.section.news

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NewsItem(
        @PrimaryKey(autoGenerate = true)
        val id: Long = 0,
        val url: String,
        val title: String,
        val groupTitle: String?,
        val tags: String,
        val date: String,
        val author: String?,
        val comments: String
)
