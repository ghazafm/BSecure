package com.mawar.bsecure.ui.view.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String , val icon: ImageVector) {
    object Community : Screen("community", "Beranda", Icons.Filled.Home)
    object FakeCall : Screen("fakecall", "Panggilan Palsu", Icons.Filled.Call)
    object Location : Screen("location", "Lokasi", Icons.Filled.LocationOn)
    object Profile : Screen("profile", "Profil", Icons.Filled.Person)
}

