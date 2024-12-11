package com.mawar.bsecure.ui.view.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
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
import com.mawar.bsecure.repository.SosRepository
import com.mawar.bsecure.ui.view.Beranda.Bottom
import com.mawar.bsecure.ui.view.Beranda.TopBars
import com.mawar.bsecure.ui.viewModel.sos.SosViewModel


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LocationScreen(
    navController: NavHostController,
    username: String,
    email: String,
    profilePictureUrl: String,
    uid: String,
    viewModel: LocationViewModel = hiltViewModel()
) {
    val viewModelsos = SosViewModel(SosRepository())
    Scaffold(
        topBar = { TopBars() },
        bottomBar = { Bottom(viewModel = viewModelsos,navController, userName = username, email = email, profilePictureUrl = profilePictureUrl, uid = uid) }
    ) { innerPadding ->
        val locationPermissionState = rememberPermissionState(android.Manifest.permission.ACCESS_FINE_LOCATION)
        val cameraPositionState = rememberCameraPositionState {
            position = com.google.android.gms.maps.model.CameraPosition.fromLatLngZoom(LatLng(-50.200000, 156.816666), 10f)
        }

        // Observe current location from the ViewModel
        val currentLocation by viewModel.location.collectAsState()

        LaunchedEffect(locationPermissionState.status.isGranted) {
            if (locationPermissionState.status.isGranted) {
                viewModel.startLocationUpdates() // Start location updates
            } else {
                locationPermissionState.launchPermissionRequest()
            }
        }

        if (locationPermissionState.status.isGranted) {
            GoogleMap(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                cameraPositionState = cameraPositionState,
                properties = MapProperties(isMyLocationEnabled = true)
            ) {
                currentLocation?.let { location ->
                    // Update camera position to follow the user's location
                    LaunchedEffect(location) {
                        cameraPositionState.position = com.google.android.gms.maps.model.CameraPosition.fromLatLngZoom(location, 15f)
                    }

                    // Add a marker at the user's current location
                    Marker(
                        state = rememberMarkerState(position = location),
                        title = "Lokasi Anda",
                        snippet = "Ini adalah lokasi terkini Anda"
                    )
                }
            }
        } else {
            Text("Location permission is required to display the map.")
        }
    }
}




