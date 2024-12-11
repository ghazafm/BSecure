package com.mawar.bsecure.model.comment

data class Comment(
    val id: String = "",
    val uid: String = "",
    val content: String = "",
    val timestamp: Long = 0L
)