package com.mawar.bsecure.data.emergency

import android.annotation.SuppressLint
import com.google.firebase.firestore.FirebaseFirestore
import com.mawar.bsecure.data.emergency.EmergencyService
import com.mawar.bsecure.data.emergency.EmergencyServiceData

object FirestoreHelper {

    @SuppressLint("StaticFieldLeak")
    private val firestore = FirebaseFirestore.getInstance()

    fun saveEmergencyServicesToFirestore() {
        val emergencyServices = EmergencyServiceData.getEmergencyServices()

        for (service in emergencyServices) {
            val serviceMap = mapOf(
                "name" to service.name,
                "phoneNumbers" to service.phoneNumbers,
                "iconResId" to service.iconResId, // Jika ingin menyimpan URL icon, ubah ke String
                "locations" to service.locations.map { location ->
                    mapOf(
                        "id" to location.id,
                        "latitude" to location.latitude,
                        "longitude" to location.longitude
                    )
                }
            )

            firestore.collection("emergency_services")
                .add(serviceMap)
                .addOnSuccessListener { documentReference ->
                    println("DocumentSnapshot added with ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    println("Error adding document: $e")
                }
        }
    }
}