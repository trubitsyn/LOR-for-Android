package dev.trubitsyn.lorforandroid.ui.section.tracker

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TrackerItem(
        @PrimaryKey
        val id: Long,
        val url: String,
        val title: String,
        val groupTitle: String?,
        val tags: String,
        val date: String,
        val author: String?,
        val comments: String
)
