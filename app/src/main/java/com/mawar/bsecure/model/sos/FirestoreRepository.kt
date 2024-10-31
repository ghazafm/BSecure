package com.mawar.bsecure.model.sos

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirestoreRepository {
    private val db = FirebaseFirestore.getInstance()
    private val userCollection = db.collection("user")

    suspend fun getUserById(id: String): User? {
        return try {
            val document = userCollection.document(id).get().await()
            document.toObject(User::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
