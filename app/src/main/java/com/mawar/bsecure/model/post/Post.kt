package com.mawar.bsecure.model.post

data class Post(
    val id: String = "", // Unique identifier for each post (document name)
    val uid: String = "", // User ID of the post creator
    val content: String = "", // The post content
    val timestamp: Long = 0L, // Timestamp for when the post was created
    val isLikedByCurrentUser: Boolean = false
)
