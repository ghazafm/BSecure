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
import androidx.navigation.NavHostController
import com.google.android.gms.location.LocationServices
import com.mawar.bsecure.data.emergency.EmergencyService
import com.mawar.bsecure.data.emergency.EmergencyServiceData
import com.mawar.bsecure.data.emergency.FirestoreEmergencyService
import com.mawar.bsecure.model.sos.Sos
import com.mawar.bsecure.ui.view.screen.EmergencyServicesDialog
import com.mawar.bsecure.ui.viewModel.sos.SosViewModel
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
        val showDialog = remember { mutableStateOf(false) }
        val context = LocalContext.current
        val emergencyServices = EmergencyServiceData.getEmergencyServices()
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        val currentAddress = remember { mutableStateOf("") }

        LaunchedEffect(Unit) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                    location?.let {
                        val geocoder = Geocoder(context, Locale.getDefault())
                        val addresses = geocoder.getFromLocation(it.latitude, it.longitude, 1)
                        if (addresses != null && addresses.isNotEmpty()) {
                            val address = addresses[0]
                            // Format: Country, Province, City, District, Sub-district
                            viewModel.getSosByLocation(negara = address.countryName, provinsi = address.adminArea, kota = address.locality, kecamatan = address.subAdminArea, kelurahan = address.subLocality)
                            val formattedAddress = "${address.countryName}, ${address.adminArea}, ${address.locality}, ${address.subAdminArea}, ${address.subLocality}"
                            Log.d("LocationAddress", "Obtained address: $formattedAddress")

                            currentAddress.value = formattedAddress
                        } else {
                            // Handle the case where no addresses are found
                            currentAddress.value = "Location not found"
                        }

                    }
                }
            }
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
        val sosList = viewModel.sosList.collectAsState().value

        if (showDialog.value) {
            EmergencyServicesDialog(
                showDialog = showDialog,
                emergencyServices = emergencyServices,
                context = context,
                userLocation = userLocation,
                userAddress = currentAddress.value, // Pass the current address to the dialog
                sos = sosList
            )
        }
    }
}

@Composable
fun EmergencyServicesDialog(showDialog: MutableState<Boolean>, emergencyServices: List<EmergencyService>, context: Context, userLocation: Location, userAddress: String) {
    if (showDialog.value) {
        Dialog(
            onDismissRequest = { showDialog.value = false }
        ) {
            Surface(
                shape = RoundedCornerShape(28.dp),
                color = MaterialTheme.colors.background,
                elevation = 8.dp
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Layanan Darurat di $userAddress", // Show the formatted address
                        style = MaterialTheme.typography.h6,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    Divider()

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 300.dp)
                            .verticalScroll(rememberScrollState())
                            .padding(vertical = 8.dp)
                    ) {
                        emergencyServices.forEach { service ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                                    .background(
                                        color = MaterialTheme.colors.surface,
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .clickable {
                                        val userLatitude = userLocation.latitude
                                        val userLongitude = userLocation.longitude
                                        FirestoreEmergencyService.callNearestService(context, userLatitude, userLongitude, service)
                                    }
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(60.dp)
                                        .clip(CircleShape)
                                        .background(Color.White),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Image(
                                        painter = painterResource(id = service.iconResId),
                                        contentDescription = "Icon ${service.name}",
                                        modifier = Modifier.size(40.dp),
                                        contentScale = ContentScale.Fit
                                    )
                                }

                                Spacer(modifier = Modifier.width(16.dp))

                                Column {
                                    Text(
                                        text = service.name,
                                        style = MaterialTheme.typography.body1,
                                        color = MaterialTheme.colors.onSurface,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = service.phoneNumbers.joinToString(", "),
                                        style = MaterialTheme.typography.body2,
                                        color = MaterialTheme.colors.secondary
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(
                            onClick = { showDialog.value = false },
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = MaterialTheme.colors.primary
                            )
                        ) {
                            Text("Tutup")
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun floatSOS() {
    FloatingActionButton(
        onClick = {},
        containerColor = Color.Red,
        shape = CircleShape,
        modifier = Modifier.size(80.dp) // Menentukan ukuran FloatingActionButton
    ) {
        Text(text = "SOS", color = Color.White, fontSize = 20.sp)
    }
}


@Composable
fun floatBar(onMenuClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(56.dp)
            .background(Color(0xFF7346A5), shape = CircleShape)
            .clickable { onMenuClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.Menu,
            contentDescription = "Menu",
            tint = Color.White
        )
    }
}


@Composable
fun floatNotif() {
    FloatingActionButton(
        onClick = {},
        containerColor = Color(0xFF7346A5),
        contentColor = Color.White
    ) {
        Icon(Icons.Filled.Notifications, contentDescription = "Notification")
    }
}

@Composable
fun card(
    painter : Painter,
    contentDescriptoin : String,
    title: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = Modifier.fillMaxWidth()
            .padding(top = 25.dp, start = 16.dp, end = 16.dp),
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(6.dp),

        ) {
        Box(modifier = Modifier.height(150.dp)){
            Image(
                painter = painter,
                contentDescription = contentDescriptoin,
                contentScale = ContentScale.Crop
            )

            Box(modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
                contentAlignment = Alignment.BottomStart

            ){
                Text(text = title,
                    fontSize = 16.sp)
            }

        }

    }

}
@Composable
fun card2(
    painter : Painter,
    contentDescriptoin : String,
    title: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = Modifier.fillMaxWidth(0.5f)
            .padding(top = 25.dp, start = 16.dp, end = 16.dp),
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(6.dp),

        ) {
        Box(modifier = Modifier.height(150.dp)) {
            Image(
                painter = painter,
                contentDescription = contentDescriptoin,
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                contentAlignment = Alignment.BottomStart

            ) {
                Text(
                    text = title,
                    fontSize = 16.sp
                )
            }

        }

    }

}


