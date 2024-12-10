package com.mawar.bsecure.login

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.mawar.bsecure.R
import com.mawar.bsecure.model.LoginModel
import com.mawar.bsecure.model.AppUser
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


@Composable
fun LoginScreen(navController: NavHostController, loginModel: LoginModel) {
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    var isLoading by remember { mutableStateOf(false) }
    var loginError by remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()

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
                modifier = Modifier
                    .align(Alignment.End)
                    .clickable { navController.navigate("forget") }
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Assuming appUser is the fetched user data from Firestore
            Button(
                onClick = {
                    isLoading = true
                    coroutineScope.launch {
                        val appUser = loginModel.login(email.text, password.text)
                        isLoading = false
                        if (appUser != null) {
                            // Debug log for login result
                            println("Login result: appUser = ${appUser.username}, ${appUser.email}")

                            if (appUser.username.isNotEmpty() && appUser.email.isNotEmpty()) {
                                // Encode the profile picture URL
                                val encodedProfilePictureUrl = URLEncoder.encode(appUser.profilePictureUrl, StandardCharsets.UTF_8.toString())
                                navController.navigate("profile/${appUser.username}/${appUser.email}/$encodedProfilePictureUrl/${appUser.uid}")
                            } else {
                                loginError = "Login failed. User data is incomplete."
                            }
                        } else {
                            loginError = "Login failed. Please check your credentials."
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                } else {
                    Text(text = "Masuk", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
            }





            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Belum punya akun? Daftar sekarang!",
                fontSize = 14.sp,
                color = Color(0xFFB285D4),
                modifier = Modifier.clickable { navController.navigate("register") }
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
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .size(40.dp)
                        .clickable {
                            loginModel.signInWithGoogle(
                                onSuccess = { appUser ->
                                    // Encode the profile picture URL
                                    Log.d("LoginScreen", "Navigating to community screen with username: ${appUser.uid}")
                                    val encodedProfilePictureUrl = URLEncoder.encode(appUser.profilePictureUrl, StandardCharsets.UTF_8.toString())
                                    navController.navigate("profile/${appUser.username}/${appUser.email}/$encodedProfilePictureUrl/${appUser.uid}")
                                },
                                onFailure = { e ->
                                    loginError = "Google sign-in failed: ${e.message}"
                                    e.printStackTrace()
                                }
                            )
                        } // Calls Google sign-in
                )
            }
        }
    }
}


