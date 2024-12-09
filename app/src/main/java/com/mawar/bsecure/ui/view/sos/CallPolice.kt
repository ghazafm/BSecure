package com.mawar.bsecure.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import com.mawar.bsecure.R

class CallActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CallScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CallScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF7B1FA2),
                    titleContentColor = Color.White
                ),
                navigationIcon = {
                    IconButton(onClick = { /* Aksi untuk kembali */ }) {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown, // Ikon panah
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* Aksi untuk video */ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_launcher_background), // Ikon video
                            contentDescription = "Video",
                            tint = Color.White
                        )
                    }
                    IconButton(onClick = { /* Aksi untuk lebih banyak */ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_launcher_background), // Ikon more
                            contentDescription = "More",
                            tint = Color.White
                        )
                    }
                }
            )
        },
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF151D26)) // Latar belakang biru tua
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    // Pesan keamanan panggilan dengan spasi tambahan di bawahnya
                    Text(
                        text = "Calls to this number are now secured\nwith end-to-end encryption.",
                        color = Color.Gray,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 60.dp) // Tambah jarak lebih besar
                    )

                    // Gambar profil
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_background), // Ganti dengan gambar profil yang sesuai
                        contentDescription = "Profile Image",
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            ,
                        contentScale = ContentScale.Crop
                    )

                    // Nama kontak dan status
                    Text(
                        text = "Police",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        modifier = Modifier.padding(top = 16.dp)

                    )
                    Text(
                        text = "Calling...",
                        color = Color.Gray,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(bottom = 32.dp)
                    )

                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 32.dp)
                    ) {
                        // Tombol Video
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Button(
                                onClick = { /* Aksi Video */ },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF333333)),
                                shape = CircleShape,
                                modifier = Modifier.size(64.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.videocam),
                                    contentDescription = "Video",
                                    tint = Color.White,
                                    modifier = Modifier.size(32.dp)
                                )
                            }
                            Text(
                                text = "Video",
                                color = Color.White,
                                fontSize = 12.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }

                        // Tombol Mute
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Button(
                                onClick = { /* Aksi Mute */ },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                                shape = CircleShape,
                                modifier = Modifier.size(64.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.mic),
                                    contentDescription = "Mute",
                                    tint = Color.White,
                                    modifier = Modifier.size(32.dp)
                                )
                            }
                            Text(
                                text = "Mute",
                                color = Color.White,
                                fontSize = 12.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }

                        // Tombol End Call
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Button(
                                onClick = { /* Aksi End Call */ },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE53935)),
                                shape = CircleShape,
                                modifier = Modifier.size(64.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.end),
                                    contentDescription = "End",
                                    tint = Color.White,
                                    modifier = Modifier.size(32.dp)
                                )
                            }
                            Text(
                                text = "End",
                                color = Color.White,
                                fontSize = 12.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Tombol Speaker
                    OutlinedButton(
                        onClick = { /* Aksi Speaker */ },
                        shape = CircleShape,
                        colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White),
                        modifier = Modifier
                            .width(140.dp)
                            .height(48.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.speaker), // Ikon Speaker
                                contentDescription = "Speaker",
                                tint = Color(0xFF151D26), // Warna ikon biru tua
                                modifier = Modifier.size(20.dp).padding(end = 8.dp)
                            )
                            Text(
                                text = "Speaker",
                                color = Color(0xFF151D26),
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun CallScreenPreview() {
    CallScreen()
}
