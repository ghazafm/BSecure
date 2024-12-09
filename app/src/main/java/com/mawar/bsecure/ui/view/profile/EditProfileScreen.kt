package com.mawar.bsecure.ui.view.profile

import AuthRepository
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.mawar.bsecure.R
import com.mawar.bsecure.model.AppUser
import getInputStreamFromUri
import kotlinx.coroutines.launch
import uploadImage
import java.io.IOException

@Composable
fun EditProfileScreen(
    navController: NavHostController,
    userName: String,
    email: String,
    profilePictureUrl: String,
    authRepository: AuthRepository,
    userDetailsState: MutableState<AppUser ?>,
    verifiedPhoneNumber: String = ""
) {
    val context = LocalContext.current
    var name by remember { mutableStateOf(TextFieldValue(userName)) }
    var emailState by remember { mutableStateOf(TextFieldValue(email)) }
    var isLoading by remember { mutableStateOf(false) }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val coroutineScope = rememberCoroutineScope()
    var newProfilePictureUrl by remember { mutableStateOf(profilePictureUrl) }
    var phoneNumber by remember { mutableStateOf(TextFieldValue(verifiedPhoneNumber)) }

    // Ambil profil pengguna
    LaunchedEffect(Unit) {
        // Ambil profil pengguna
        val userProfile = authRepository.getUserProfile()
        if (userProfile != null) {
            phoneNumber = TextFieldValue(userProfile.phoneNumber ?: "")
        }

        // Observe changes in new phone number
        navController.currentBackStackEntry?.savedStateHandle?.getLiveData<String>("newPhoneNumber")
            ?.observeForever() { newPhoneNumber ->
                if (newPhoneNumber != null) {
                    phoneNumber = TextFieldValue(newPhoneNumber) // Update phoneNumber dengan nomor baru
                }
            }
    }

    val imagePickerLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            selectedImageUri = uri
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header
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
                    text = "Ubah Profil",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.weight(1f))
            }
        }

        // Form Edit Profil
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
                    .clickable { imagePickerLauncher.launch("image/*") }
            ) {
                val profilePicturePainter = rememberImagePainter(data = selectedImageUri ?: newProfilePictureUrl)

                Image(
                    painter = profilePicturePainter,
                    contentDescription = "Edit Foto Profil",
                    modifier = Modifier
                        .size(125.dp)
                        .background(Color.Gray, CircleShape),
                    contentScale = ContentScale.Crop
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
            ReadOnlyField(label = "Email", text = email)
            PhoneFieldWithIcon(
                label = "Nomor Telepon",
                value = phoneNumber,
                onClick = {
                    navController.navigate("otp") // Navigasi ke PhoneScreen
                    // Set nomor // Set nomor telepon baru setelah kembali dari PhoneScreen
                    navController.currentBackStackEntry?.savedStateHandle?.getLiveData<String>("newPhoneNumber")?.observeForever { newPhoneNumber ->
                        if (newPhoneNumber != null) {
                            phoneNumber = TextFieldValue(newPhoneNumber) // Update phoneNumber dengan nomor baru
                        }
                    }
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Tombol Simpan
            Button(
                onClick = {
                    isLoading = true
                    coroutineScope.launch {
                        try {
                            val imageUrl = selectedImageUri?.let { uri ->
                                val inputStream = getInputStreamFromUri(context, uri)
                                inputStream?.let {
                                    uploadImage(it)
                                } ?: throw IOException("Failed to open input stream")
                            } ?: newProfilePictureUrl

                            saveProfile(
                                authRepository,
                                name.text,
                                emailState.text,
                                imageUrl,
                                phoneNumber.text, // Simpan nomor telepon
                                navController,
                                userDetailsState
                            )
                        } catch (e: Exception) {
                            isLoading = false
                            // Handle error (e.g., show a Toast or Snackbar)
                        } finally {
                            isLoading = false
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5A2D82)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                } else {
                    Text(text = "Simpan", color = Color.White, fontSize = 16.sp)
                }
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

private suspend fun saveProfile(
    authRepository: AuthRepository,
    name: String,
    email: String,
    imageUrl: String,
    phoneNumber: String, // Tambahkan parameter nomor telepon
    navController: NavHostController,
    userDetailsState: MutableState<AppUser?>
) {
    val success = authRepository.updateProfile(name, email, imageUrl, phoneNumber)
    if (success) {
        val updatedUser = authRepository.getUserProfile()
        userDetailsState.value = updatedUser
        navController.popBackStack()
    } else {
        // Handle save failure
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReadOnlyField(label: String, text: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = label,
            fontSize = 16.sp,
            color = Color.Black
        )
        OutlinedTextField(
            value = text,
            onValueChange = {},
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            enabled = false, // Set enabled to false to make the field read-only
            shape = RoundedCornerShape(32.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                disabledTextColor = Color.Black,
                disabledBorderColor = Color.Gray,
                disabledLabelColor = Color.Gray
            )
        )
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileTextField(
    label: String,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    onClick: () -> Unit = {} // Default empty lambda for optional click behavior
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() } // Make the whole field clickable
    ) {
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
            ),
            readOnly = true // Make the text field read-only
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhoneFieldWithIcon(
    label: String,
    value: TextFieldValue,
    onClick: () -> Unit // Navigasi ke PhoneScreen
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        // Label untuk field
        Text(
            text = label,
            fontSize = 16.sp,
            color = Color.Black
        )

        // OutlinedTextField dengan ikon klikable
        OutlinedTextField(
            value = value,
            onValueChange = {}, // Tidak ada perubahan nilai
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            shape = RoundedCornerShape(32.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Gray,
                unfocusedBorderColor = Color.Gray
            ),
            readOnly = true, // Tetapkan sebagai read-only
            trailingIcon = {
                IconButton(onClick = { onClick() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.phone), // Ganti dengan ID ikon Anda
                        contentDescription = "Edit Phone Number",
                        tint = Color.Gray
                    )
                }
            }
        )
    }
}
