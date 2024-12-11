package com.mawar.bsecure.ui.view.screen

import android.content.Context
import android.location.Location
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.mawar.bsecure.data.emergency.EmergencyService
import com.mawar.bsecure.data.emergency.EmergencyServiceData
import com.mawar.bsecure.data.emergency.EmergencyServiceUtils
import com.mawar.bsecure.data.emergency.ServiceLocation
import com.mawar.bsecure.ui.view.Beranda.Bottom
import com.mawar.bsecure.ui.view.Beranda.TopBars
import com.mawar.bsecure.ui.view.Beranda.floatBar

import com.mawar.bsecure.ui.view.Beranda.floatNotif
import kotlinx.coroutines.launch

@Composable
fun SOSScreen(
    navController: NavHostController,
    username: String,
    email: String,
    profilePictureUrl: String,
    uid: String
) {
    val context = LocalContext.current
    val emergencyServices = EmergencyServiceData.getEmergencyServices()
    val showDialog = remember { mutableStateOf(false) }

    Scaffold(
        topBar = { TopBars() },
        bottomBar = { Bottom(navController, userName = username, email = email, profilePictureUrl = profilePictureUrl, uid=uid) }
    ) {
            innerPadding ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
                .padding(innerPadding)
                .background(Brush.verticalGradient(colors = listOf(Color(0xFFF1EFEF), Color(0x97C5BBBB))))
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Button(
                    onClick = { showDialog.value = true },
                    modifier = Modifier
                        .size(150.dp),
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(Color.Red)
                ) {
                    Text("SOS", fontSize = 40.sp, color = Color.White)
                }

                Spacer(modifier = Modifier.height(40.dp))
                val userLocation = Location("").apply {
                    // Ganti dengan koordinat lokasi pengguna yang sebenarnya
                    latitude = -7.980800
                    longitude = 112.645500
                }
                if (showDialog.value) {
                    EmergencyServicesDialog(
                        showDialog = showDialog,
                        emergencyServices = emergencyServices,
                        context = context,
                        userLocation = userLocation
                    )
                }
            }
        }
    }
}





@Composable
fun EmergencyServicesDialog(
    showDialog: MutableState<Boolean>,
    emergencyServices: List<EmergencyService>,
    context: Context,
    userLocation: Location
) {
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
                        text = "Layanan Darurat",
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
                                        EmergencyServiceUtils.getNearestEmergencyService(context, userLocation)

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

// Fungsi untuk mencari lokasi terdekat
private fun findNearestLocation(locations: List<ServiceLocation>, userLocation: Location): ServiceLocation? {
    var nearestLocation: ServiceLocation? = null
    var nearestDistance: Float = Float.MAX_VALUE

    locations.forEach { location ->
        val serviceLocation = Location("").apply {
            latitude = location.latitude
            longitude = location.longitude
        }
        val distance = userLocation.distanceTo(serviceLocation)
        if (distance < nearestDistance) {
            nearestDistance = distance
            nearestLocation = location
        }
    }

    return nearestLocation
}


