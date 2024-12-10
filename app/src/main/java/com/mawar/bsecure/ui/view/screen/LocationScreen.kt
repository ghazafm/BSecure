package com.mawar.bsecure.ui.view.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.android.gms.maps.model.LatLng
import com.mawar.bsecure.ui.viewModel.location.LocationViewModel
import dagger.hilt.android.HiltAndroidApp
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberMarkerState



@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LocationScreen(
    viewModel: LocationViewModel = hiltViewModel()  // Get the ViewModel using Hilt or other DI
) {
    val locationPermissionState = rememberPermissionState(android.Manifest.permission.ACCESS_FINE_LOCATION)
    val cameraPositionState = rememberCameraPositionState {
        position = com.google.android.gms.maps.model.CameraPosition.fromLatLngZoom(LatLng(-50.200000, 156.816666), 10f) // Default to Jakarta
    }

    // Observe location updates from the ViewModel
    val currentLocation by viewModel.location.collectAsState()

    // State untuk menyimpan lokasi marker
    var markerPosition by remember { mutableStateOf<LatLng?>(null) }

    LaunchedEffect(locationPermissionState.status.isGranted) {
        if (locationPermissionState.status.isGranted) {
            viewModel.updateLocation()  // Request location updates
        } else {
            locationPermissionState.launchPermissionRequest()
        }
    }

    if (locationPermissionState.status.isGranted) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = MapProperties(isMyLocationEnabled = true)
        ) {
            // Jika ada lokasi saat ini, update posisi kamera
            currentLocation?.let { location ->
                LaunchedEffect(location) {
                    cameraPositionState.position = com.google.android.gms.maps.model.CameraPosition.fromLatLngZoom(location, 15f)
                }

                // Menambahkan marker di lokasi saat ini
                markerPosition = location
            }

            // Jika markerPosition tidak null, tampilkan marker
            markerPosition?.let { position ->
                val markerState = rememberMarkerState(position = position)

                Marker(
                    state = markerState,
                    title = "Panggilan Darurat",
                    snippet = "Lokasi saat ini"
                )
            }

        }
    } else {
        Text("Location permission is required to display the map.")
    }
}


