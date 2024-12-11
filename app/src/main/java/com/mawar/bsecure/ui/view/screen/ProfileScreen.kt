package com.mawar.bsecure.ui.view.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ProfileScreen() {
    Surface(color = Color(0xFFFFCDD2)) {
        Text(text = "Profile Screen", modifier = Modifier.padding(16.dp))
    }
}