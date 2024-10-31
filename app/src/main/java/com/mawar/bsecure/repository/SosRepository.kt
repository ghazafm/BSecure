package com.mawar.bsecure.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.mawar.bsecure.model.sos.Sos
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class SosRepository {

    private val db = FirebaseFirestore.getInstance()

    fun getAllSos(): Flow<List<Sos>> = flow {
        try {
            val snapshot = db.collection("sos").get().await()
            val sosList = snapshot.documents.mapNotNull { it.toObject<Sos>() }
            emit(sosList)
        } catch (e: Exception) {
            emit(emptyList())
        }
    }

    suspend fun addSos(sos: Sos) {
//        db.collection("sos").document(sos.id).set(sos).await()
        db.collection("sos").add(sos).await()
    }
}