package com.mawar.bsecure.ui.view.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mawar.bsecure.R

@Preview
@Composable
fun KontakScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
                IconButton(onClick = { /* Handle back button click */ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_keyboard_backspace_24),
                        contentDescription = "Back",
                        tint = Color.White,
                        modifier = Modifier.size(30.dp)
                    )
                }

                Spacer(modifier = Modifier.weight(0.65f))

                Text(
                    text = "Kontak Kami",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.weight(1f))
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ContactOption(
                    title = "Call us",
                    iconRes = R.drawable.baseline_phone_24, // Replace with phone icon resource
                    description = "Our team is online\nMon-Fri • 9-17",
                    onClick = { /* Handle call action */ }
                )
                ContactOption(
                    title = "Email us",
                    iconRes = R.drawable.baseline_email_24, // Replace with email icon resource
                    description = "Our team is online\nMon-Fri • 9-17",
//                    modifier = Modifier.padding(vertical = 8.dp),
                    onClick = { /* Handle call action */ }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Contact us in Social Media",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Gray,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            // Social Media Contact Options
            SocialMediaOption("Instagram", "4,6K Followers • 118 Posts", R.drawable.instagram) { /* Handle Instagram click */ }
            SocialMediaOption("Facebook", "3,8K Followers • 136 Posts", R.drawable.facebook) { /* Handle Instagram click */ }
            SocialMediaOption("WhatsApp", "Available Mon-Fri • 9-17", R.drawable.whatsapp) { /* Handle Instagram click */ }
        }
    }
}

@Composable
fun ContactOption(title: String, iconRes: Int, description: String, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .background(Color(0xFFF0F0F0), shape = RoundedCornerShape(12.dp))
            .padding(16.dp)
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = title,
            tint = Color.Black,
            modifier = Modifier.size(36.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = description,
            fontSize = 12.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun SocialMediaOption(name: String, details: String, iconRes: Int, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
//            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
            .background(Color(0xFFF7F7F7), shape = RoundedCornerShape(8.dp))
            .padding(16.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = name,
            modifier = Modifier.size(36.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                text = name,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = details,
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}