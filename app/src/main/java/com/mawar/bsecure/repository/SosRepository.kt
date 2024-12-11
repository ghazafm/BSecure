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

    suspend fun getAllSos(): Flow<List<Sos>> = flow {
        try {
            val snapshot = db.collection("sos").get().await()
            val sosList = snapshot.documents.mapNotNull { it.toObject<Sos>() }
            emit(sosList)
        } catch (e: Exception) {
            emit(emptyList())
        }
    }

    suspend fun addSos(sos: Sos) {
        db.collection("sos").document(sos.id).set(sos).await()
    }

    suspend fun getSosByLocation(
        negara: String = "",
        provinsi: String = "",
        kota: String = "",
        kecamatan: String = "",
        kelurahan: String = ""
    ): Flow<List<Sos>> = flow {
        try {
            var snapshot = db.collection("sos")
                .whereEqualTo("negara", negara)
                .apply {
                    if (provinsi.isNotBlank()) this.whereEqualTo("provinsi", provinsi)
                    if (kota.isNotBlank()) this.whereEqualTo("kota", kota)
                    if (kecamatan.isNotBlank()) this.whereEqualTo("kecamatan", kecamatan)
                    if (kelurahan.isNotBlank()) this.whereEqualTo("kelurahan", kelurahan)
                }
                .get()
                .await()

            var sosList = snapshot.documents.mapNotNull { it.toObject<Sos>() }

            if (sosList.isEmpty() && kelurahan.isNotBlank()) {
                snapshot = db.collection("sos")
                    .whereEqualTo("negara", negara)
                    .apply {
                        if (provinsi.isNotBlank()) this.whereEqualTo("provinsi", provinsi)
                        if (kota.isNotBlank()) this.whereEqualTo("kota", kota)
                        if (kecamatan.isNotBlank()) this.whereEqualTo("kecamatan", kecamatan)
                    }
                    .get()
                    .await()
                sosList = snapshot.documents.mapNotNull { it.toObject<Sos>() }
            }

            if (sosList.isEmpty() && kecamatan.isNotBlank()) {
                snapshot = db.collection("sos")
                    .whereEqualTo("negara", negara)
                    .apply {
                        if (provinsi.isNotBlank()) this.whereEqualTo("provinsi", provinsi)
                        if (kota.isNotBlank()) this.whereEqualTo("kota", kota)
                    }
                    .get()
                    .await()
                sosList = snapshot.documents.mapNotNull { it.toObject<Sos>() }
            }

            if (sosList.isEmpty() && kota.isNotBlank()) {
                snapshot = db.collection("sos")
                    .whereEqualTo("negara", negara)
                    .apply {
                        if (provinsi.isNotBlank()) this.whereEqualTo("provinsi", provinsi)
                    }
                    .get()
                    .await()
                sosList = snapshot.documents.mapNotNull { it.toObject<Sos>() }
            }

            if (sosList.isEmpty() && provinsi.isNotBlank()) {
                snapshot = db.collection("sos")
                    .whereEqualTo("negara", negara)
                    .get()
                    .await()
                sosList = snapshot.documents.mapNotNull { it.toObject<Sos>() }
            }

            // Filter hanya satu per jenis (Polisi, Rumah Sakit, Pemadam Kebakaran)
            val uniqueSosList = sosList
                .groupBy { it.jenis }
                .mapNotNull { (_, sosByJenis) -> sosByJenis.firstOrNull() }

            emit(uniqueSosList)
        } catch (e: Exception) {
            Log.e("SosRepository", "Error fetching SOS by location: ${e.message}", e)
            emit(emptyList())
        }
    }

}