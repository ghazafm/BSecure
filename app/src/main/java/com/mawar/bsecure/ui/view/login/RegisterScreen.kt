package com.mawar.bsecure.login

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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mawar.bsecure.R

@Preview
@Composable
fun RegisterScreen() {
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var username by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    var confirmPassword by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF5A2D82))
            .padding(top = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(10.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.width(8.dp))

            IconButton(onClick = { /* Handle back button click */ }) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(color = Color(0xFF9C43F5), shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_arrow_back_ios_new_24),
                        contentDescription = "Back",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(90.dp))

            Text(
                text = "Daftar",
                fontSize = 32.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White, RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
                .padding(horizontal = 32.dp, vertical = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            Image(
                painter = painterResource(id = R.drawable.bsecure),
                contentDescription = "Bsecure",
                modifier = Modifier
                    .fillMaxWidth()
                    .size(60.dp)
            )

            Spacer(modifier = Modifier.height(30.dp))

            HorizontalDivider(thickness = 1.dp, color = Color.Gray)

            Spacer(modifier = Modifier.height(25.dp))

            Text(
                text = "Masukkan email Anda",
                fontSize = 16.sp,
                color = Color.Black
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Masukkan email Anda") },
                placeholder = { Text("email@gmail.com") },
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Masukkan username Anda",
                fontSize = 16.sp,
                color = Color.Black
            )

            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Masukkan username Anda") },
                placeholder = { Text("Username") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Masukkan password Anda") },
                placeholder = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Masukkan kembali password Anda") },
                placeholder = { Text("Re-type Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = { /* Handle register click */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5A2D82)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Daftar", color = Color.White, fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(horizontalAlignment = Alignment.CenterHorizontally)
            {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Sudah punya akun? ",
                        fontSize = 14.sp,
                        color = Color.Black
                    )

                    Text(
                        text = "Masuk sekarang!",
                        fontSize = 14.sp,
                        color = Color(0xFFB285D4),
                        modifier = Modifier.clickable { /* Handle login click */ }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    HorizontalDivider(
                        modifier = Modifier
                            .weight(1f)
                            .height(1.dp),
                        color = Color.Gray
                    )
                    Text(
                        text = " atau ",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                    HorizontalDivider(
                        modifier = Modifier
                            .weight(1f)
                            .height(1.dp),
                        color = Color.Gray
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.google),
                        contentDescription = "Google",
                        tint = Color.Unspecified,
                        modifier = Modifier
                            .size(40.dp)
                            .clickable { /* Handle Google sign-in */ }
                    )
                }
            }
        }
    }
}