package com.mawar.bsecure.ui.view.Beranda

import android.icu.text.CaseMap.Title
import android.location.Location
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSizeIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
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
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import androidx.navigation.NavHostController
import com.mawar.bsecure.data.emergency.EmergencyServiceData
import com.mawar.bsecure.ui.view.screen.EmergencyServicesDialog
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


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
fun Bottom(navController: NavHostController, userName: String, email: String, profilePictureUrl: String, uid: String) {
    Box(modifier = Modifier.fillMaxWidth()) {
        NavigationBar (
            modifier = Modifier.align(Alignment.BottomCenter),
            containerColor = Color(0xFF7346A5)
        ){
            NavigationBarItem(
                icon = { Icon(Icons.Filled.AddCircle, contentDescription = "community") },
                label = { Text("Komunitas") },
                selected = true,
                onClick = {},
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

@Preview
@Composable
private fun cardPrev() {


}
@Preview
@Composable
private fun notifPrev() {
    floatNotif()
}

@Preview
@Composable
private fun floatbarPrev() {

}

@Preview
@Composable
private fun topPrev() {
    TopBars()

}

@Preview
@Composable
private fun botPrev() {
//    Bottom()

}

@Preview
@Composable
private fun floatPrev() {
    floatSOS()
}