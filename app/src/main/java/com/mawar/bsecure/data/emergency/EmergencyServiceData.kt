package com.mawar.bsecure.data.emergency

import com.mawar.bsecure.R

object EmergencyServiceData {

    fun getEmergencyServices(): List<EmergencyService> {
        return listOf(
            EmergencyService(
                name = "Pemadam Kebakaran",
                phoneNumbers = listOf("082137780052", "082137780052", "082137780052"), // Beberapa nomor telepon
                iconResId = R.drawable.facebook,
                locations = listOf(
                    ServiceLocation("123-1", -7.966620, 112.632632),
                    ServiceLocation("123-2", -7.970800, 112.635500),
                    ServiceLocation("123-3", -8.971420, 115.634870)
                )
            ),
            EmergencyService(
                name = "Rumah Sakit",
                phoneNumbers = listOf("082137780052", "082137780052", "082137780052"), // Beberapa nomor telepon
                iconResId = R.drawable.google,
                locations = listOf(
                    ServiceLocation("124-1", -7.972000, 112.632000),
                    ServiceLocation("124-2", -7.975000, 112.630000),
                    ServiceLocation("124-3", -7.974000, 112.631000)
                )
            ),
            EmergencyService(
                name = "Polisi",
                phoneNumbers = listOf("082137780052", "082137780052", "082137780052"), // Beberapa nomor telepon
                iconResId = R.drawable.apple,
                locations = listOf(
                    ServiceLocation("125-1", -7.971000, 112.626000),
                    ServiceLocation("125-2", -7.973000, 112.627000),
                    ServiceLocation("125-3", -7.970500, 112.625500)
                )
            )
        )
    }
}
