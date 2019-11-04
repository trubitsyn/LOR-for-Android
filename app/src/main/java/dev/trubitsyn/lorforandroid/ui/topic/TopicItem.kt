package dev.trubitsyn.lorforandroid.ui.topic

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TopicItem(
        @PrimaryKey(autoGenerate = true)
        val id: Long = 0,
        val url: String,
        val title: String,
        val tags: String,
        val message: String,
        val postDate: String,
        //val lastModified: Date,
        val sticky: Boolean,
        val commentsCount: Int,
        val favsCount: Int,
        val watchCount: Int,
        val postScore: Int,
        val author: String
)