package com.mawar.bsecure.data.emergency

import android.content.Context
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.mawar.bsecure.model.sos.Sos
import com.mawar.bsecure.ui.view.screen.findNearestLocation
import kotlinx.coroutines.tasks.await

object FirestoreEmergencyService {

    private val firestore = FirebaseFirestore.getInstance()

    suspend fun getEmergencyServicesFromFirestore(): List<EmergencyService> {
        val emergencyServices = mutableListOf<EmergencyService>()

        try {
            val snapshot = firestore.collection("emergency_services").get().await()
            for (document in snapshot.documents) {
                val service = document.toObject<EmergencyService>()
                service?.let {
                    emergencyServices.add(it)
                }
            }
        } catch (e: Exception) {
            // Handle exceptions, e.g., log the error
            e.printStackTrace()
        }

        return emergencyServices
    }
    fun callNearestService(
        context: Context,
        service: Sos
    ) {
        val phoneNumber = service.no_telp.takeIf { it.isNotEmpty() }

        if (phoneNumber != null) {
            PhoneUtils.makePhoneCall(context, phoneNumber)
        } else {
            Toast.makeText(context, "No available contact for the selected service", Toast.LENGTH_LONG).show()
        }
    }

}