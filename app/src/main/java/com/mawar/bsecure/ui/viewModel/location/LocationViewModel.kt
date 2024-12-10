package com.mawar.bsecure.ui.viewModel.location

import android.Manifest
import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class LocationViewModel(application: Application) : AndroidViewModel(application) {

    private val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(application)

    private val _location = MutableStateFlow<LatLng?>(null)
    val location: StateFlow<LatLng?> = _location

    @SuppressLint("MissingPermission")
    fun updateLocation() {
        viewModelScope.launch {
            fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
                location?.let {
                    _location.value = LatLng(it.latitude, it.longitude)
                }
            }
        }
    }
}