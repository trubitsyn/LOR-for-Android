package dev.trubitsyn.lorforandroid.ui.topic

import dev.trubitsyn.lorforandroid.api.model.Topic

interface TopicRepository {
    fun getTopic(): Topic
}