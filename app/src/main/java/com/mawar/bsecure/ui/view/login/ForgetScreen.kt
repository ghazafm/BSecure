package com.mawar.bsecure.ui.view.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mawar.bsecure.R
import com.mawar.bsecure.model.sendPasswordResetEmail

@Composable
fun ForgotScreen(navController: NavController) {
    var email by remember { mutableStateOf(TextFieldValue("")) }
    val context = LocalContext.current // Get the current context

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF5A2D82))
            .padding(top = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(10.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    painter = painterResource(id = R.drawable.back1),
                    contentDescription = "Back",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(80.dp)
                )
            }

            Spacer(modifier = Modifier.width(50.dp))

            Text(
                text = "Lupa Password",
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
                .padding(horizontal = 32.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
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

            Divider(color = Color.Gray, thickness = 1.dp)

            Spacer(modifier = Modifier.height(30.dp))

            Image(
                painter = painterResource(id = R.drawable.padlock),
                contentDescription = "Lock Icon",
                modifier = Modifier.size(225.dp)
            )

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "Lupa Password?",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF5A2D82),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Jangan khawatir! Masukkan emailmu dan kami akan bantu pulihkan akunmu",
                fontSize = 14.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Masukkan email Anda") },
                placeholder = { Text("email@gmail.com") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    val emailText = email.text
                    if (emailText.isNotEmpty()) {
                        sendPasswordResetEmail(
                            context = context,
                            email = emailText,
                            onSuccess = {
                                Toast.makeText(context, "Email reset password berhasil dikirim", Toast.LENGTH_SHORT).show()
                            },
                            onFailure = { e ->
                                Toast.makeText(context, "Gagal mengirim email reset: ${e.message}", Toast.LENGTH_SHORT).show()
                            },
                            updateFirestoreAfterReset = true // Enable Firestore update
                        )
                    } else {
                        Toast.makeText(context, "Masukkan email terlebih dahulu", Toast.LENGTH_SHORT).show()
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5A2D82)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Kirim URL Verifikasi", color = Color.White, fontSize = 16.sp)
            }

        }
    }
}
