package com.mawar.bsecure

import AuthRepository
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import com.mawar.bsecure.ui.theme.BSecureTheme
import com.mawar.bsecure.ui.view.login.ForgotScreen
import com.mawar.bsecure.ui.view.profile.ProfileScreen
import com.mawar.bsecure.welcome.WelcomeActivity
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class MainActivity : ComponentActivity() {
    private lateinit var loginModel: LoginModel
    private lateinit var authRepository: AuthRepository
    private lateinit var googleSignInLauncher: ActivityResultLauncher<Intent>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
                AppNavigation(loginModel, authRepository, googleSignInLauncher,userDetailsState)
            }
        }
    }
}

@Composable
fun AppNavigation(
    loginModel: LoginModel,
    authRepository: AuthRepository,
    googleSignInLauncher: ActivityResultLauncher<Intent>,
    userDetailsState: MutableState<AppUser?>
) {
    val navController = rememberNavController()

    // Observe userDetailsState for navigation
    userDetailsState.value?.let { appUser ->
        val username = appUser.username
        val email = appUser.email
        val encodedProfilePictureUrl = URLEncoder.encode(appUser.profilePictureUrl, StandardCharsets.UTF_8.toString())

        if (username.isNotEmpty() && email.isNotEmpty()) {
            println("Navigating to profile with username: $username, email: $email")
            navController.navigate("profile/$username/$email/$encodedProfilePictureUrl")
        }
        userDetailsState.value = null // Reset the state to avoid repeated navigation
    }

    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen(navController, loginModel) }
        composable(
            "profile/{username}/{email}/{profilePictureUrl}",
            arguments = listOf(
                navArgument("username") { type = NavType.StringType; defaultValue = "" },
                navArgument("email") { type = NavType.StringType; defaultValue = "" },
                navArgument("profilePictureUrl") { type = NavType.StringType; defaultValue = "" }
            )
        ) { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: ""
            val email = backStackEntry.arguments?.getString("email") ?: ""
            val profilePictureUrlEncoded = backStackEntry.arguments?.getString("profilePictureUrl") ?: ""

            // Decode the profile picture URL
            val profilePictureUrl = URLDecoder.decode(profilePictureUrlEncoded, StandardCharsets.UTF_8.toString())

            ProfileScreen(userName = username, email = email, profilePictureUrl = profilePictureUrl)
        }

        composable("register") { RegisterScreen(navController, authRepository, googleSignInLauncher) }
        composable("forget") { ForgotScreen(navController) }
    }
}
