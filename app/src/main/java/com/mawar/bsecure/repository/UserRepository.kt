package com.mawar.bsecure.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.mawar.bsecure.model.AppUser
import kotlinx.coroutines.tasks.await

class UserRepository {

    private val firestore = FirebaseFirestore.getInstance()

    suspend fun getUserData(uid: String): AppUser? {
        return try {
            val document = firestore.collection("users").document(uid).get().await()
            document.toObject(AppUser::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
