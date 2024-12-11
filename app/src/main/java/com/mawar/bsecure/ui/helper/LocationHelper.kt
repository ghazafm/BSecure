package com.mawar.bsecure.ui.helper

import android.content.Context
import android.location.Geocoder
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import java.util.Locale

object LocationHelper {
    fun getLastKnownLocation(
        context: Context,
        fusedLocationClient: FusedLocationProviderClient,
        onAddressReceived: (String?) -> Unit
    ) {
        fusedLocationClient.lastLocation.addOnCompleteListener { task ->
            if (task.isSuccessful && task.result != null) {
                val location = task.result
                val geocoder = Geocoder(context, Locale.getDefault())
                try {
                    val addressList = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                    if (!addressList.isNullOrEmpty()) {
                        val address = addressList[0]
                        val formattedAddress = """
                        Negara: ${address.countryName}
                        Provinsi: ${address.adminArea}
                        Kota: ${address.locality ?: address.subAdminArea}
                        Kecamatan: ${address.subLocality}
                        Kelurahan: ${address.thoroughfare ?: address.subThoroughfare}
                    """.trimIndent()
                        onAddressReceived(formattedAddress)
                        Log.d("LocationLog", "Alamat terkini: $formattedAddress")
                    } else {
                        onAddressReceived(null)
                        Log.d("LocationLog", "Gagal mendapatkan alamat.")
                    }
                } catch (e: Exception) {
                    onAddressReceived(null)
                    Log.e("LocationLog", "Error geocoding: ${e.message}")
                }
            } else {
                onAddressReceived(null)
                Log.d("LocationLog", "Gagal mendapatkan lokasi.")
            }
        }
    }
}