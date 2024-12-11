package com.mawar.bsecure.ui.view.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.mawar.bsecure.R
import com.mawar.bsecure.ui.view.Beranda.Bottom
import com.mawar.bsecure.ui.view.Beranda.TopBars
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun ProfileScreen(navController: NavHostController, username: String, email: String, profilePictureUrl: String, uid: String) {
    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF5A2D82), shape = RoundedCornerShape(bottomEnd = 30.dp, bottomStart = 30.dp))
                    .height(100.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val encodedProfilePictureUrl = URLEncoder.encode(profilePictureUrl, StandardCharsets.UTF_8.toString())
                    IconButton(onClick = {navController.navigate("sos/$username/$email/$encodedProfilePictureUrl/$uid")}) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_keyboard_backspace_24),
                            contentDescription = "Back",
                            tint = Color.White,
                            modifier = Modifier.size(30.dp)
                        )
                    }

                    Spacer(modifier = Modifier.weight(0.65f))

                    Text(
                        text = "Profile",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.weight(1f))
                }}
        },
        bottomBar = { Bottom(navController, userName = username, email = email, profilePictureUrl = profilePictureUrl, uid=uid) }

    ) {
        innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(100.dp))
                // Use default image if profilePictureUrl is empty
                val profilePicture = if (profilePictureUrl.isNotEmpty()) {
                    rememberImagePainter(data = profilePictureUrl)
                } else {
                    painterResource(id = R.drawable.user) // Use the default image
                }
                Image(
                    painter = profilePicture,
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(125.dp)
                        .clip(CircleShape)
                        .background(Color.Gray, CircleShape),
                    contentScale = ContentScale.Crop
                )



                Spacer(modifier = Modifier.height(12.dp))

                // User name and handle
                Text(
                    text = "$username",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Text(
                    text = "$email",
                    fontSize = 16.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(30.dp))

                ProfileOption("Ubah Profil"){
                    val encodedProfilePictureUrl = URLEncoder.encode(profilePictureUrl, StandardCharsets.UTF_8.toString())
                    navController.navigate("edit_profile/$username/$email/$encodedProfilePictureUrl")
                }
                ProfileOption("Terhubung Dengan Kami"){
                    navController.navigate("contact")
                }
                ProfileOption("Kebijakan"){
                    navController.navigate("kebijakan")
                }
                ProfileOption("Community"){
                    navController.navigate("community/$uid")
                }
            }
        }
    }




@Composable
fun ProfileOption(text: String, onClick: () -> Unit = {}) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                fontSize = 16.sp,
                color = Color.Black,
                fontWeight = FontWeight.Medium
            )
            Icon(
                painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                contentDescription = "Arrow Right",
                tint = Color.Gray
            )
        }
        HorizontalDivider(thickness = 0.5.dp, color = Color.Gray)
    }
}