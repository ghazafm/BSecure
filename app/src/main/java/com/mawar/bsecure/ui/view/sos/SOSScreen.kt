//package com.mawar.bsecure.ui
//
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.compose.foundation.background
//import androidx.compose.foundation.border
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.*
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.navigation.NavHostController
//import com.mawar.bsecure.R
//
//class SOSActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            SOSScreen(navController, username, email, profilePictureUrl, uid)
//        }
//    }
//}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun SOSScreen(
//    navController: NavHostController,
//    userName: String,
//    email: String,
//    profilePictureUrl: String,
//    uid: String
//) {
//    val showPopup = remember { mutableStateOf(false) }
//
//    Scaffold(
//        bottomBar = {
//            BottomAppBar(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(60.dp),
//                containerColor = Color(0xFF7B1FA2)
//            ) {
//                // Bottom Navigation Items
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.SpaceEvenly,
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    BottomNavItem(iconRes = R.drawable.google, label = "Komunitas")
//                    BottomNavItem(iconRes = R.drawable.google, label = "Panggilan Palsu")
//                    Spacer(modifier = Modifier.width(56.dp)) // Spacer for SOS Button
//                    BottomNavItem(iconRes = R.drawable.google, label = "I'm Here")
//                    BottomNavItem(iconRes = R.drawable.google, label = "Profil")
//                }
//            }
//        },
//        floatingActionButton = {
//            FloatingActionButton(
//                onClick = { showPopup.value = true },
//                containerColor = Color.Red,
//                shape = CircleShape,
//                modifier = Modifier.size(70.dp)
//            ) {
//                Text(text = "SOS", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
//            }
//        },
//        floatingActionButtonPosition = FabPosition.Center,
//        content = { innerPadding ->
//            Box(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(innerPadding)
//                    .background(Color.White),
//                contentAlignment = Alignment.Center
//            ) {
//                if (showPopup.value) {
//                    EmergencyPopup(onDismiss = { showPopup.value = false })
//                }
//            }
//        }
//    )
//}
//
//@Composable
//fun BottomNavItem(iconRes: Int, label: String) {
//    Column(horizontalAlignment = Alignment.CenterHorizontally) {
//        Icon(
//            painter = painterResource(id = iconRes),
//            contentDescription = label,
//            tint = Color.White,
//            modifier = Modifier.size(24.dp)
//        )
//        Text(text = label, color = Color.White, fontSize = 10.sp)
//    }
//}
//
//@Composable
//fun EmergencyPopup(onDismiss: () -> Unit) {
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.Black.copy(alpha = 0.5f))
//            .clickable { onDismiss() }, // Dismiss when clicking outside
//        contentAlignment = Alignment.Center
//    ) {
//        Column(
//            modifier = Modifier
//                .width(300.dp)
//                .background(Color.White, RoundedCornerShape(16.dp))
//                .padding(16.dp)
//        ) {
//            // Close Button (X) at the top right
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.End
//            ) {
//                Icon(
//                    painter = painterResource(id = R.drawable.x), // Close icon resource
//                    contentDescription = "Close",
//                    tint = Color.Unspecified,
//                    modifier = Modifier
//                        .size(24.dp)
//                        .clickable { onDismiss() }
//                )
//            }
//
//            EmergencyOptions()
//        }
//    }
//}
//
//@Composable
//fun EmergencyOptions() {
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center,
//        modifier = Modifier.padding(16.dp)
//    ) {
//        // Warning Icon
//        Icon(
//            painter = painterResource(id = R.drawable.warning),
//            contentDescription = "Warning",
//            tint = Color.Unspecified,
//            modifier = Modifier
//                .size(100.dp)
//                .background(Color.White, CircleShape)
//                .padding(8.dp)
//        )
//
//        Spacer(modifier = Modifier.height(8.dp))
//
//        // Question Prompt
//        Text(
//            text = "Apa yang bisa kami bantu?",
//            color = Color(0xFF7B1FA2),
//            fontWeight = FontWeight.Bold,
//            fontSize = 18.sp,
//            textAlign = TextAlign.Center
//        )
//
//        Spacer(modifier = Modifier.height(12.dp))
//
//        // Emergency Option Buttons
//        Row(
//            horizontalArrangement = Arrangement.SpaceEvenly,
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            EmergencyOptionButton(
//                iconRes = R.drawable.hospital,
//                label = "Rumah sakit",
//
//                backgroundColor = Color.White
//            )
//            EmergencyOptionButton(
//                iconRes = R.drawable.fire,
//                label = "Kebakaran",
//
//                backgroundColor = Color.White
//            )
//        }
//
//        Spacer(modifier = Modifier.height(12.dp))
//
//        // "Tindakan Kriminal" tanpa border
//        EmergencyOptionButton(
//            iconRes = R.drawable.criminal,
//            label = "Tindakan Kriminal",
//            backgroundColor = Color.Red,
//            fullWidth = true,
//            showBorder = false // Set showBorder to false
//        )
//    }
//}
//
//@Composable
//fun EmergencyOptionButton(
//    iconRes: Int,
//    label: String,
//    backgroundColor: Color,
//    fullWidth: Boolean = false,
//    showBorder: Boolean = true // Parameter baru untuk border
//) {
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        modifier = Modifier
//            .padding(8.dp)
//            .background(backgroundColor, RoundedCornerShape(8.dp))
//            .let {
//                if (showBorder) it.border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(8.dp))
//                else it // Apply border only if showBorder is true
//            }
//            .let {
//                if (fullWidth) it.fillMaxWidth(0.8f) else it.wrapContentWidth()
//            }
//            .clickable { /* Handle click */ }
//            .padding(16.dp)
//    ) {
//        Icon(
//            painter = painterResource(id = iconRes),
//            contentDescription = label,
//            tint = Color.Unspecified,
//            modifier = Modifier.size(40.dp)
//        )
//        Spacer(modifier = Modifier.height(8.dp))
//        Text(
//            text = label,
//            color = if (backgroundColor == Color.White) Color.Magenta else Color.Black,
//            fontSize = 13.sp,
//            textAlign = TextAlign.Center,
//
//        )
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun SOSScreenPreview() {
//    SOSScreen(navController, username, email, profilePictureUrl, uid)
//}
