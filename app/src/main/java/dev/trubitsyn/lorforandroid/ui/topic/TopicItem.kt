package dev.trubitsyn.lorforandroid.ui.topic

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class TopicItem(
        @PrimaryKey
        val id: Long,
        val url: String,
        val title: String,
        val tags: List<String>,
        val message: String,
        val postDate: Date,
        val lastModified: Date,
        val sticky: Boolean,
        val commentsCount: Int,
        val favsCount: Int,
        val watchCount: Int,
        val postScore: Int,
        val author: String
)