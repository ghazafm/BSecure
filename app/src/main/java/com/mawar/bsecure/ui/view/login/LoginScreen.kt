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
fun LoginScreen() {
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF5A2D82)) // Background color for the top part
            .padding(top = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(50.dp))

        Text(
            text = "Masuk",
            fontSize = 32.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(80.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White, RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(30.dp))

            Image(
                painter = painterResource(id = R.drawable.bsecure),
                contentDescription = "Bsecure",
                modifier = Modifier
                    .fillMaxWidth()
                    .size(80.dp)
            )

            Spacer(modifier = Modifier.height(30.dp))

            Divider(color = Color.Gray, thickness = 1.dp)

            Spacer(modifier = Modifier.height(30.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Masukkan email/username Anda") },
                placeholder = { Text("email@gmail.com") },
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

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Lupa password Anda?",
                fontSize = 14.sp,
                color = Color(0xFFB285D4),
                modifier = Modifier.align(Alignment.End).clickable { /* Handle forgot password */ }
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = { /* Handle login click */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5A2D82)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Masuk", color = Color.White, fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Belum punya akun? Daftar sekarang!",
                fontSize = 14.sp,
                color = Color(0xFFB285D4),
                modifier = Modifier.clickable { /* Handle register click */ }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Divider(color = Color.Gray, thickness = 1.dp)

                Text(
                    text = " atau ",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

                Divider(color = Color.Gray, thickness = 1.dp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.google),
                    contentDescription = "Google",
                    tint = Color.Unspecified, // This prevents any tint from being applied
                    modifier = Modifier
                        .size(40.dp)
                        .clickable { /* Handle Apple sign-in */ }
                )
            }
        }
    }
}