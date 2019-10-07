package dev.trubitsyn.lorforandroid.ui.comment

import dev.trubitsyn.lorforandroid.api.model.Comment

interface CommentRepository {
    fun getComments(topic: String, offset: Int): List<Comment>
}