package com.mawar.bsecure.ui.view.fakecall

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.Image
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mawar.bsecure.R

@Composable
fun IncomingCallScreen(contactName: String) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // Background Image
        Image(
            painter = painterResource(id = R.drawable.blur),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Nama Kontak
            Text(
                text = contactName,
                fontSize = 32.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(50.dp))

            // Tombol Remind Me dan Message
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    IconButton(onClick = { /* Remind Me Action */ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.clock),
                            contentDescription = "Remind Me",
                            tint = Color.White,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                    Text(text = "Remind Me", color = Color.White, fontSize = 12.sp)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    IconButton(onClick = { /* Message Action */ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.message),
                            contentDescription = "Message",
                            tint = Color.White,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                    Text(text = "Message", color = Color.White, fontSize = 12.sp)
                }
            }

            Spacer(modifier = Modifier.height(50.dp))

            // Tombol Decline dan Accept
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    IconButton(onClick = { /* Decline Call Action */ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.declice),
                            contentDescription = "Decline",
                            tint = Color.Unspecified,
                            modifier = Modifier.size(64.dp)
                        )
                    }
                    Text(text = "Decline", color = Color.White, fontSize = 14.sp)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    IconButton(onClick = { /* Accept Call Action */ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.accept),
                            contentDescription = "Accept",
                            tint = Color.Unspecified,
                            modifier = Modifier.size(64.dp)
                        )
                    }
                    Text(text = "Accept", color = Color.White, fontSize = 14.sp)
                }
            }
        }
    }
}
