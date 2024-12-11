package com.mawar.bsecure.data.emergency

data class EmergencyService(
    val name: String = "",
    val phoneNumbers: List<String> = emptyList(),
    val iconResId: Int = 0, // Untuk penyimpanan bisa gunakan URL atau nama resource
    val locations: List<ServiceLocation> = emptyList()
)
