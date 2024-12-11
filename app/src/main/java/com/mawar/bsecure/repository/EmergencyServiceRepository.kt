package com.mawar.bsecure.repository

import android.annotation.SuppressLint
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.mawar.bsecure.data.emergency.EmergencyService
import com.mawar.bsecure.data.emergency.ServiceLocation

object EmergencyServiceRepository {
    @SuppressLint("StaticFieldLeak")
    private val firestore = FirebaseFirestore.getInstance()

    fun fetchEmergencyServices(onComplete: (List<EmergencyService>) -> Unit) {
        firestore.collection("emergency_services").get()
            .addOnSuccessListener { documents ->
                val services = documents.mapNotNull { document ->
                    val name = document.getString("name")
                    val phoneNumbers = document.get("phone_numbers") as? List<String>
                    val iconResId = document.getString("icon_res_id") // Pastikan ini sesuai dengan yang Anda simpan
                    val locations = document.get("locations") as? List<Map<String, Any>>

                    if (name != null && phoneNumbers != null && iconResId != null && locations != null) {
                        EmergencyService(
                            name = name,
                            phoneNumbers = phoneNumbers,
                            iconResId = iconResId.toInt(), // Konversi sesuai cara Anda menyimpan ID drawable
                            locations = locations.map {
                                ServiceLocation(
                                    it["phone"] as String,
                                    it["latitude"] as Double,
                                    it["longitude"] as Double
                                )
                            }
                        )
                    } else {
                        null
                    }
                }
                Log.d("FirestoreData", "Fetched ${services.size} services") // Tambahkan log ini
                onComplete(services)
            }
            .addOnFailureListener { exception ->
                Log.e("FirestoreError", "Error fetching services: ", exception) // Log error
            }
    }
}