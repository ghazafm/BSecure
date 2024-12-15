package com.mawar.bsecure.ui.view.Beranda

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.icu.text.CaseMap.Title
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSizeIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.app.ActivityCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.android.gms.location.LocationServices
import com.mawar.bsecure.data.emergency.EmergencyService
import com.mawar.bsecure.data.emergency.EmergencyServiceData
import com.mawar.bsecure.data.emergency.FirestoreEmergencyService
import com.mawar.bsecure.model.sos.Sos
import com.mawar.bsecure.ui.view.screen.EmergencyServicesDialog
import com.mawar.bsecure.ui.viewModel.sos.SosViewModel
import kotlinx.coroutines.tasks.await
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBars() {

    CenterAlignedTopAppBar(
        modifier = Modifier.requiredSizeIn(maxHeight = 70.dp),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF7346A5),
            titleContentColor = Color.White,

            ),
        title = { Text(text = "Bsecure")},

        )

}



@Composable
fun Bottom(viewModel: SosViewModel, navController: NavHostController, userName: String, email: String, profilePictureUrl: String, uid: String) {
    val showDialog = remember { mutableStateOf(false) }
    val context = LocalContext.current
    val emergencyServices = EmergencyServiceData.getEmergencyServices()
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    val currentAddress = remember { mutableStateOf("") }
    val sosList by viewModel.sosList.collectAsState()

    LaunchedEffect(currentAddress.value) {
        try {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                val location = fusedLocationClient.lastLocation.await() // Convert to suspend function
                location?.let {
                    val geocoder = Geocoder(context, Locale.getDefault())
                    val addresses = geocoder.getFromLocation(it.latitude, it.longitude, 1)
                    if (!addresses.isNullOrEmpty()) {
                        val address = addresses[0]
                        currentAddress.value = "${address.countryName}, ${address.adminArea}, ${address.locality}, ${address.subAdminArea}, ${address.subLocality}"
                        viewModel.getSosByLocation(
                            negara = address.countryName,
                            provinsi = address.adminArea,
                            kota = address.locality,
                            kecamatan = address.subAdminArea,
                            kelurahan = address.subLocality
                        )
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("Bottom", "Error getting location", e)
        }
    }

    Box(modifier = Modifier.fillMaxWidth()) {
        NavigationBar (
            modifier = Modifier.align(Alignment.BottomCenter),
            containerColor = Color(0xFF7346A5)
        ){
            NavigationBarItem(
                icon = { Icon(Icons.Filled.AddCircle, contentDescription = "community") },
                label = { Text("Komunitas") },
                selected = true,
                onClick = {navController.navigate("community/$uid")},
                colors = NavigationBarItemColors(
                    selectedTextColor = Color.White,
                    selectedIndicatorColor = Color.Transparent,
                    selectedIconColor = Color.White,
                    unselectedIconColor = Color.White,
                    unselectedTextColor = Color.White,
                    disabledIconColor = Color.White,
                    disabledTextColor = Color.White
                )
            )

            NavigationBarItem(
                icon = { Icon(Icons.Filled.Call, contentDescription = "call") },
                label = { Text("Panggilan Palsu", textAlign = TextAlign.Center) },
                selected = true,
                onClick = {
                    val encodedProfilePictureUrl = URLEncoder.encode(profilePictureUrl, StandardCharsets.UTF_8.toString())
                    navController.navigate("fakecall/$userName/$email/$encodedProfilePictureUrl/$uid")
                },
                colors = NavigationBarItemColors(
                    selectedTextColor = Color.White,
                    selectedIndicatorColor = Color.Transparent,
                    selectedIconColor = Color.White,
                    unselectedIconColor = Color.White,
                    unselectedTextColor = Color.White,
                    disabledIconColor = Color.White,
                    disabledTextColor = Color.White
                )
            )

            Spacer(modifier = Modifier.weight(1f)) // Space for SOS Button

            NavigationBarItem(
                icon = { Icon(Icons.Filled.LocationOn, contentDescription = "location") },
                label = { Text("Lokasi") },
                selected = true,
                onClick = {
                    val encodedProfilePictureUrl = URLEncoder.encode(profilePictureUrl, StandardCharsets.UTF_8.toString())
                    navController.navigate("location/$userName/$email/$encodedProfilePictureUrl/$uid")
                },
                colors = NavigationBarItemColors(
                    selectedTextColor = Color.White,
                    selectedIndicatorColor = Color.Transparent,
                    selectedIconColor = Color.White,
                    unselectedIconColor = Color.White,
                    unselectedTextColor = Color.White,
                    disabledIconColor = Color.White,
                    disabledTextColor = Color.White
                )
            )

            NavigationBarItem(
                icon = { Icon(Icons.Filled.Person, contentDescription = "profile") },
                label = { Text("Profil") },
                selected = true,
                onClick = {
                    val encodedProfilePictureUrl = URLEncoder.encode(profilePictureUrl, StandardCharsets.UTF_8.toString())
                    navController.navigate("profile/$userName/$email/$encodedProfilePictureUrl/$uid")
                },
                colors = NavigationBarItemColors(
                    selectedTextColor = Color.White,
                    selectedIndicatorColor = Color.Transparent,
                    selectedIconColor = Color.White,
                    unselectedIconColor = Color.White,
                    unselectedTextColor = Color.White,
                    disabledIconColor = Color.White,
                    disabledTextColor = Color.White
                )
            )
        }

        // Floating SOS Button
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(y = (-60).dp)
                .background(Color.Red, shape = CircleShape)
                .size(85.dp)
                .clickable { showDialog.value = true },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "SOS",
                color = Color.White,
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp),
                textAlign = TextAlign.Center
            )
        }
        val userLocation = Location("").apply {
            // Ganti dengan koordinat lokasi pengguna yang sebenarnya
            latitude = -7.980800
            longitude = 112.645500
        }
        Log.d("Beranda", "SOS List: ${sosList.size}")

        val isLoading = viewModel.isLoading.collectAsState()
        if (showDialog.value) {
            Log.d("Beranda", "Showing dialog $sosList")
            EmergencyServicesDialog(
                showDialog = showDialog,
                context = context,
                userAddress = currentAddress.value, // Pass the current address to the dialog
                sos = sosList
            )
        }
    }
}

//@Composable
//fun Bottom(viewModel: SosViewModel = hiltViewModel(), navController: NavHostController, userName: String, email: String, profilePictureUrl: String, uid: String) { // Use viewModel() to ensure correct ViewModel instance
//    val context = LocalContext.current
//    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
//    val sosList by viewModel.sosList.collectAsState()
//    val currentAddress = remember { mutableStateOf("") }
//
//    LaunchedEffect(currentAddress.value) {
//        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            val location = fusedLocationClient.lastLocation.await() // Suspended to ensure we get result
//            location?.let {
//                val geocoder = Geocoder(context, Locale.getDefault())
//                val addresses = geocoder.getFromLocation(it.latitude, it.longitude, 1)
//                if (!addresses.isNullOrEmpty()) {
//                    val address = addresses[0]
//                    currentAddress.value = "${address.countryName}, ${address.adminArea}, ${address.locality}, ${address.subAdminArea}, ${address.subLocality}"
//                    viewModel.getSosByLocation(
//                        negara = address.countryName,
//                        provinsi = address.adminArea,
//                        kota = address.locality,
//                        kecamatan = address.subAdminArea,
//                        kelurahan = address.subLocality
//                    )
//                }
//            }
//        }
//    }
//
//    Box(modifier = Modifier.fillMaxSize()) {
//        Column {
//            Text(text = "SOS List: ${sosList.size}")
//            sosList.forEach { sos ->
//                Text(text = "Name: ${sos.nama_instansi}")
//            }
//        }
//    }
//}
