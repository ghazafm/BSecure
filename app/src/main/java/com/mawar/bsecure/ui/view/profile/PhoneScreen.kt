package com.mawar.bsecure.ui.view.profile

import android.app.Activity
import android.widget.Toast
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.mawar.bsecure.R
import com.mawar.bsecure.repository.PhoneAuthRepository
import java.util.concurrent.TimeUnit
@Composable
fun PhoneScreen(
    navController: NavController,
    phoneAuthRepository: PhoneAuthRepository
) {
    var phoneNumber by remember { mutableStateOf(TextFieldValue("")) }
    var otp by remember { mutableStateOf(TextFieldValue("")) }
    var isOtpSent by remember { mutableStateOf(false) }
    var verificationId by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val activity = LocalContext.current as? Activity

    if (activity == null) {
        Toast.makeText(context, "Context is not an Activity", Toast.LENGTH_SHORT).show()
        return
    }

    // Nonaktifkan reCAPTCHA untuk pengujian
    val firebaseAuth = FirebaseAuth.getInstance()
    firebaseAuth.firebaseAuthSettings.setAppVerificationDisabledForTesting(true)

    val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            Toast.makeText(context, "Verifikasi otomatis berhasil!", Toast.LENGTH_SHORT).show()
            isLoading = false
            phoneAuthRepository.updatePhoneNumber(
                credential = credential,
                onSuccess = {
                    Toast.makeText(context, "Nomor telepon berhasil disetel!", Toast.LENGTH_SHORT).show()
                    navController.popBackStack()
                },
                onError = { error ->
                    Toast.makeText(context, "Error: $error", Toast.LENGTH_SHORT).show()
                }
            )
        }

        override fun onVerificationFailed(e: FirebaseException) {
            Toast.makeText(context, "Verifikasi gagal: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
            isLoading = false
        }

        override fun onCodeSent(sentVerificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
            verificationId = sentVerificationId
            isOtpSent = true
            isLoading = false
            Toast.makeText(context, "OTP dikirim ke +62${phoneNumber.text}", Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF5A2D82))
            .padding(top = 30.dp),
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
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_keyboard_backspace_24),
                        contentDescription = "Back",
                        tint = Color.White,
                        modifier = Modifier.size(30.dp)
                    )
                }

                Spacer(modifier = Modifier.weight(0.65f))

                Text(
                    text = "Verifikasi Nomor Telepon",
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
                .fillMaxSize()
                .background(Color.White, RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
                .padding(horizontal = 32.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (!isOtpSent) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "+62", fontSize = 18.sp, color = Color.Gray, modifier = Modifier.padding(end = 8.dp))
                    OutlinedTextField(
                        value = phoneNumber,
                        onValueChange = { phoneNumber = it },
                        label = { Text("Nomor Telepon") },
                        placeholder = { Text("8123456789") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = {
                        if (phoneNumber.text.isNotEmpty()) {
                            isLoading = true
                            phoneAuthRepository.sendOtp(
                                phoneNumber = "+62${phoneNumber.text}",
                                activity = activity,
                                callbacks = callbacks
                            )
                        } else {
                            Toast.makeText(context, "Masukkan nomor telepon terlebih dahulu", Toast.LENGTH_SHORT).show()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5A2D82)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = if (isLoading) "Mengirim..." else "Kirim OTP", color = Color.White, fontSize = 16.sp)
                }
            } else {
                OutlinedTextField(
                    value = otp,
                    onValueChange = { otp = it },
                    label = { Text("Masukkan OTP") },
                    placeholder = { Text("123456") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = {
                        phoneAuthRepository.verifyOtp(
                            verificationId = verificationId!!,
                            otp = otp.text,
                            onSuccess = { credential ->
                                phoneAuthRepository.updatePhoneNumber(
                                    credential = credential,
                                    onSuccess = {
                                        Toast.makeText(context, "Nomor telepon berhasil disetel!", Toast.LENGTH_SHORT).show()
                                        navController.previousBackStackEntry?.savedStateHandle?.set("newPhoneNumber", "+62${phoneNumber.text}")
                                        navController.popBackStack()
                                    },
                                    onError = { error ->
                                        Toast.makeText(context, "Error: $error", Toast.LENGTH_SHORT).show()
                                    }
                                )
                            },
                            onError = { error ->
                                Toast.makeText(context, "Error: $error", Toast.LENGTH_SHORT).show()
                            }
                        )
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5A2D82)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Verifikasi", color = Color.White, fontSize = 16.sp)
                }
            }
        }
    }
}
