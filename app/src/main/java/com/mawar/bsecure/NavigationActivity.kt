package com.mawar.bsecure

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.mawar.bsecure.data.emergency.FirestoreHelper
import com.mawar.bsecure.ui.theme.BSecureTheme
import com.mawar.bsecure.ui.view.screen.MainScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NavigationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        FirestoreHelper.saveEmergencyServicesToFirestore()
        setContent {
            BSecureTheme {
                MainScreen()
            }
        }
    }
}

