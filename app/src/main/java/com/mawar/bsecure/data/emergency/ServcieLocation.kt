package com.mawar.bsecure.data.emergency

import com.google.android.gms.maps.model.LatLng

data class ServiceLocation(
    val id: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
)
fun ServiceLocation.toLatLng(): LatLng {
    return LatLng(this.latitude, this.longitude)
}

