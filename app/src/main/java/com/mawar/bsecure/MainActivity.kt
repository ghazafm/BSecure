package com.mawar.bsecure

import AuthRepository
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
<<<<<<< Updated upstream
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.firebase.firestore.FirebaseFirestore
import com.mawar.bsecure.login.LoginScreen
import com.mawar.bsecure.login.RegisterScreen
import com.mawar.bsecure.model.AppUser
import com.mawar.bsecure.model.LoginModel
import com.mawar.bsecure.navigation.AppNavigation
import com.mawar.bsecure.repository.PhoneAuthRepository
import com.mawar.bsecure.ui.theme.BSecureTheme
import com.mawar.bsecure.ui.view.login.ForgotScreen
import com.mawar.bsecure.ui.view.profile.EditProfileScreen
import com.mawar.bsecure.ui.view.profile.ProfileScreen
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
=======
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mawar.bsecure.ui.view.fakecall.TimerScreen
import com.mawar.bsecure.ui.viewModel.fakecall.TimerViewModel
import com.mawar.bsecure.ui.view.fakecall.IncomingCallScreen
>>>>>>> Stashed changes

class MainActivity : ComponentActivity() {
    private lateinit var loginModel: LoginModel
    private lateinit var authRepository: AuthRepository
    private lateinit var googleSignInLauncher: ActivityResultLauncher<Intent>
    private lateinit var phoneAuthRepository: PhoneAuthRepository



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
<<<<<<< Updated upstream

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
=======
        setContent {
            MyApp()
        }
    }
}

@Composable
fun MyApp() {
    val navController = rememberNavController()
    val timerViewModel: TimerViewModel = viewModel()

    NavHost(navController = navController, startDestination = "timer_screen") {
        composable("timer_screen") {
            TimerScreen(navController = navController, timerViewModel = timerViewModel)
        }
        composable("incoming_call/{contactName}") { backStackEntry ->
            val contactName = backStackEntry.arguments?.getString("contactName") ?: "Nama Tidak Diketahui"
            IncomingCallScreen(contactName = contactName)
>>>>>>> Stashed changes
        }
    }
}

