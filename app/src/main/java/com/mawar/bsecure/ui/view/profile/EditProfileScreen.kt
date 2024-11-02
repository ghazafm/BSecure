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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mawar.bsecure.R


@Preview
@Composable
fun EditProfileScreen() {
    var name by remember { mutableStateOf(TextFieldValue("Awan Ganteng")) }
    var email by remember { mutableStateOf(TextFieldValue("LukasAwan@titit.com")) }
    var phone by remember { mutableStateOf(TextFieldValue("08696969696")) }

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
                    text = "Ubah Profil",
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
                .padding(horizontal = 32.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(125.dp)
                    .clickable { /* Handle edit profile picture click */ }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.user),
                    contentDescription = "Edit Foto Profil",
                    modifier = Modifier.size(125.dp)
                )
                Text(
                    text = "Edit foto profil",
                    color = Color.White,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .background(Color(0x99000000), CircleShape)
                        .padding(4.dp)
                        .align(Alignment.BottomCenter)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            ProfileTextField(label = "Nama", value = name, onValueChange = { name = it })
            ProfileTextField(label = "Email", value = email, onValueChange = { email = it })
            ProfileTextField(label = "No. Telp", value = phone, onValueChange = { phone = it })

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { /* Handle save click */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5A2D82)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(text = "Simpan", color = Color.White, fontSize = 16.sp)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileTextField(label: String, value: TextFieldValue, onValueChange: (TextFieldValue) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp))
    {
        Text(
            text = label,
            fontSize = 16.sp,
            color = Color.Black
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            shape = RoundedCornerShape(32.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Gray,
                unfocusedBorderColor = Color.Gray
            )
        )
    }
}