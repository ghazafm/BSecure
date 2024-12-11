package com.mawar.bsecure

import AuthRepository
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.runtime.mutableStateOf
import com.google.firebase.firestore.FirebaseFirestore
import com.mawar.bsecure.model.AppUser
import com.mawar.bsecure.model.LoginModel
import com.mawar.bsecure.navigation.AppNavigation
import com.mawar.bsecure.repository.PhoneAuthRepository
import com.mawar.bsecure.repository.SosRepository
import com.mawar.bsecure.ui.SosScreen
import com.mawar.bsecure.ui.theme.BSecureTheme
import com.mawar.bsecure.ui.viewModel.sos.SosViewModel
import com.mawar.bsecure.ui.viewModel.sos.SosViewModelFactory
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var loginModel: LoginModel
    private lateinit var authRepository: AuthRepository
    private lateinit var googleSignInLauncher: ActivityResultLauncher<Intent>
    private lateinit var phoneAuthRepository: PhoneAuthRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        phoneAuthRepository = PhoneAuthRepository()

        // Initialize the AuthRepository
        authRepository = AuthRepository()
        val userDetailsState = mutableStateOf<AppUser?>(null)


        // Define a mutable state to hold the username for navigation
        val userNameState = mutableStateOf<String?>(null)

        // Create ActivityResultLauncher for Google Sign-In
        googleSignInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val data: Intent? = result.data
            loginModel.handleGoogleSignInResult(data,
                onSuccess = { appUser ->
                    Log.d("LoginScreen", "memek2")
                    Log.d("WOI ANJENG", "${appUser.uid},${appUser.username}")
                    userDetailsState.value = appUser // Set the full AppUser object
                },
                onFailure = { e ->
                    e.printStackTrace()
                }
            )
        }

        // Initialize LoginModel with activity and launcher
        loginModel = LoginModel(
            activity = this,
            launcher = googleSignInLauncher,
            authRepository = authRepository,
            db = FirebaseFirestore.getInstance()
        )

        setContent {
            BSecureTheme {
                AppNavigation(loginModel, authRepository, googleSignInLauncher,userDetailsState,phoneAuthRepository)
            }
        }
    }

}

