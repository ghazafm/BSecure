package com.mawar.bsecure.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.mawar.bsecure.model.comment.Comment
import com.mawar.bsecure.model.post.Post
import kotlinx.coroutines.tasks.await

class CommunityRepository {

    private val firestore = FirebaseFirestore.getInstance()

    suspend fun addPost(post: Post) {
        try {
            firestore.collection("community_posts")
                .document(post.id) // Use postId as the document name
                .set(post) // Store only the Post model
                .await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun getPosts(): List<Pair<Post, Map<String, Any>?>> {
        return try {
            val snapshot = firestore.collection("community_posts").get().await()
            snapshot.documents.map { document ->
                val post = document.toObject(Post::class.java) ?: Post()
                val userData = getUserByUid(post.uid) // Fetch user data by uid
                Pair(post, userData) // Pair post with user data
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    suspend fun getUserByUid(uid: String): Map<String, Any>? {
        return try {
            val userDoc = firestore.collection("users").document(uid).get().await()
            if (userDoc.exists()) userDoc.data else null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun toggleLike(post: Post, userId: String) {
        val postRef = firestore.collection("community_posts").document(post.id)
        val likeRef = postRef.collection("likes").document(userId)

        try {
            val documentSnapshot = likeRef.get().await()
            if (documentSnapshot.exists()) {
                likeRef.delete().await()
            } else {
                likeRef.set(mapOf("userId" to userId)).await()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun getLikesCount(post: Post): Int {
        return try {
            val likesSnapshot = firestore.collection("community_posts")
                .document(post.id)
                .collection("likes")
                .get()
                .await()
            likesSnapshot.size()
        } catch (e: Exception) {
            e.printStackTrace()
            0
        }
    }

    /**
     * Get all comments for a specific post.
     * @param postId The ID of the post.
     * @return A list of Post objects that are stored as comments.
     */
    suspend fun getCommentsForPost(postId: String): List<Post> {
        return try {
            val snapshot = firestore.collection("community_posts")
                .document(postId)
                .collection("comments")
                .get()
                .await()
            snapshot.documents.map { document ->
                document.toObject(Post::class.java) ?: Post()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    suspend fun addComment(postId: String,comment: Comment){
        try {
            firestore.collection("community_posts")
                .document(postId) // Use postId as the document name
                .collection("comments")
                .document(comment.id)
                .set(comment)
                .await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
