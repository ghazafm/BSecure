package com.mawar.bsecure.data.emergency

import android.content.Context
import android.location.Location
import android.util.Log
import android.widget.Toast
import com.mawar.bsecure.repository.EmergencyServiceRepository

object EmergencyServiceUtils {

    fun getNearestEmergencyService(context: Context, userLocation: Location) {
        Log.d("EmergencyServiceUtils", "User Location: Latitude: ${userLocation.latitude}, Longitude: ${userLocation.longitude}")

        EmergencyServiceRepository.fetchEmergencyServices { services ->
            if (services.isEmpty()) {
                Toast.makeText(context, "Tidak ada layanan darurat tersedia.", Toast.LENGTH_SHORT).show()
                return@fetchEmergencyServices
            }

            var nearestService: EmergencyService? = null
            var nearestDistance: Float = Float.MAX_VALUE

            // Mencari lokasi terdekat
            services.forEach { service ->
                if (service.locations.isEmpty()) {
                    Log.w("EmergencyServiceUtils", "Service ${service.name} has no locations.")
                    return@forEach // Skip to the next service
                }

                service.locations.forEach { location ->
                    val serviceLocation = Location("").apply {
                        latitude = location.latitude
                        longitude = location.longitude
                    }

                    val distance = userLocation.distanceTo(serviceLocation)
                    Log.d("EmergencyServiceUtils", "Checking service: ${service.name}, Distance: $distance")

                    // Jika jarak lebih dekat dari yang sudah ditemukan
                    if (distance < nearestDistance) {
                        nearestDistance = distance
                        nearestService = service
                    }
                }
            }

            // Gunakan nearestService untuk melakukan panggilan atau memberikan informasi
            nearestService?.let { service ->
                val phone = service.phoneNumbers.firstOrNull() // Ambil nomor telepon pertama
                if (!phone.isNullOrEmpty()) {
                    Toast.makeText(context, "Menghubungi ${service.name} dengan nomor $phone", Toast.LENGTH_SHORT).show()
                    PhoneUtils.makePhoneCall(context, phone) // Lakukan panggilan
                } else {
                    Toast.makeText(context, "Nomor telepon untuk ${service.name} tidak tersedia", Toast.LENGTH_SHORT).show()
                }
            } ?: run {
                Toast.makeText(context, "Layanan darurat terdekat tidak ditemukan", Toast.LENGTH_SHORT).show()
            }
        }
    }
}



