package com.mawar.bsecure.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.mawar.bsecure.ui.UserScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UserScreen(userId = "123") // Ganti "123" dengan ID pengguna yang ingin Anda ambil
        }
    }
}
