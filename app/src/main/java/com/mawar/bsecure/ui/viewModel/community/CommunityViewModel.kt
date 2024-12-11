package com.mawar.bsecure.ui.viewModel.community

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mawar.bsecure.model.comment.Comment
import com.mawar.bsecure.model.post.Post
import com.mawar.bsecure.repository.CommunityRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class CommunityViewModel(private val repository: CommunityRepository) : ViewModel() {

    // UI state
    var isLoading = MutableStateFlow(false)
    var errorMessage = MutableStateFlow<String?>(null)

    // Posts list
    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    val posts: StateFlow<List<Post>> = _posts

    // Holds the list of comments for the selected post
    private val _comments = MutableStateFlow<List<Post>>(emptyList())
    val comments: StateFlow<List<Post>> = _comments

    // Like counts for each post
    private val _likesCount = MutableStateFlow<Map<String, Int>>(emptyMap())
    val likesCount: StateFlow<Map<String, Int>> = _likesCount

    // Like counts for each post
    private val _commentsCount = MutableStateFlow<Map<String, Int>>(emptyMap())
    val commentsCount: StateFlow<Map<String, Int>> = _commentsCount

    // Cache for user data (username, profile picture, etc.)
    val userCache = mutableMapOf<String, Map<String, Any>>()

    /**
     * Add a new post to Firestore.
     * @param uid User ID of the post creator.
     * @param content Content of the post.
     */
    fun addPost(uid: String, content: String) {
        viewModelScope.launch {
            try {
                isLoading.value = true
                val postId = UUID.randomUUID().toString()
                val post = Post(
                    id = postId,
                    uid = uid,
                    content = content,
                    timestamp = System.currentTimeMillis()
                )
                repository.addPost(post)
                loadPosts()
                isLoading.value = false
            } catch (e: Exception) {
                isLoading.value = false
                errorMessage.value = e.message
                Log.e("CommunityViewModel", "Error adding post", e)
            }
        }
    }

    /**
     * Load posts from Firestore and fetch the user data (like username and profile picture) for each post's UID.
     */
    fun loadPosts() {
        viewModelScope.launch {
            try {
                isLoading.value = true
                val fetchedPosts = repository.getPosts()

                // Attach user data to each post
                val postsWithUserData = fetchedPosts.map { (post, userData) ->
                    // Cache user data to avoid repeated Firestore calls
                    if (!userCache.containsKey(post.uid)) {
                        userCache[post.uid] = userData ?: emptyMap()
                    }
                    post // Do not modify the Post model
                }
                val sortedComments = postsWithUserData.sortedByDescending { it.timestamp }

                val updatedLikeCounts = mutableMapOf<String, Int>()
                fetchedPosts.forEach { (post, _) ->
                    val likeCount = repository.getLikesCount(post)
                    updatedLikeCounts[post.id] = likeCount // Store like count by post ID
                }

                val updatedCommentCounts = mutableMapOf<String, Int>()
                fetchedPosts.forEach { (post, _) ->
                    val likeCount = repository.getCommentsCount(post)
                    updatedLikeCounts[post.id] = likeCount // Store like count by post ID
                }

                // Update the posts list
                _posts.value = sortedComments // Update the list of posts
                _likesCount.value = updatedLikeCounts
                _commentsCount.value = updatedCommentCounts
                isLoading.value = false
            } catch (e: Exception) {
                isLoading.value = false
                errorMessage.value = e.message
                Log.e("CommunityViewModel", "Error loading posts", e)
            }
        }
    }

    /**
     * Toggles a like on a post.
     * If the user has liked the post before, it removes the like.
     * If not, it adds the like.
     * @param post The post to like/unlike.
     * @param userId The ID of the user liking/unliking the post.
     */
    fun toggleLike(post: Post, userId: String) {
        viewModelScope.launch {
            try {
                // Toggle like in Firestore
                repository.toggleLike(post, userId)

                // After toggling, fetch the updated like count
                val updatedCount = repository.getLikesCount(post)

                // Persist the updated like count
                _likesCount.value = _likesCount.value.toMutableMap().apply {
                    put(post.id, updatedCount)
                }
            } catch (e: Exception) {
                errorMessage.value = e.message
                Log.e("CommunityViewModel", "Error toggling like", e)
            }
        }
    }

    fun loadComments(postId: String) {
        viewModelScope.launch {
            try {
                isLoading.value = true

                // Get comments from Firestore for the post
                val fetchedComments = repository.getCommentsForPost(postId)

                // Attach user data for each comment
                val commentsWithUserData = fetchedComments.map { comment ->
                    if (!userCache.containsKey(comment.uid)) {
                        val userData = repository.getUserByUid(comment.uid)
                        userCache[comment.uid] = userData ?: emptyMap()
                    }
                    comment // Return the original comment
                }
                val sortedComments = commentsWithUserData.sortedByDescending { it.timestamp }
                _comments.value = sortedComments // Update the comments list
                isLoading.value = false
            } catch (e: Exception) {
                isLoading.value = false
                errorMessage.value = e.message
                Log.e("CommunityViewModel", "Error loading comments", e)
            }
        }
    }

    fun addComment(uid: String, content: String, postId: String) {
        viewModelScope.launch {
            try {
                isLoading.value = true
                val id = UUID.randomUUID().toString()
                val comment = Comment(
                    id = id,
                    uid = uid,
                    content = content,
                    timestamp = System.currentTimeMillis()
                )
                repository.addComment(postId, comment)
                loadComments(postId) // Refresh comments after adding a new one
                isLoading.value = false
            } catch (e: Exception) {
                isLoading.value = false
                errorMessage.value = e.message
                Log.e("CommunityViewModel", "Error adding post", e)
            }
        }
    }
}