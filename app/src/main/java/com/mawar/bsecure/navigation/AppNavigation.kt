package com.mawar.bsecure.navigation

import AuthRepository
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mawar.bsecure.login.LoginScreen
import com.mawar.bsecure.login.RegisterScreen
import com.mawar.bsecure.model.AppUser
import com.mawar.bsecure.model.LoginModel
import com.mawar.bsecure.ui.view.login.ForgotScreen
import com.mawar.bsecure.ui.view.profile.EditProfileScreen
import com.mawar.bsecure.ui.view.profile.KontakScreen
import com.mawar.bsecure.ui.view.profile.PhoneScreen
import com.mawar.bsecure.ui.view.profile.ProfileScreen
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import com.mawar.bsecure.repository.PhoneAuthRepository
import com.mawar.bsecure.ui.view.profile.KebijakanScreen

@Composable
fun AppNavigation(
    loginModel: LoginModel,
    authRepository: AuthRepository,
    googleSignInLauncher: ActivityResultLauncher<Intent>,
    userDetailsState: MutableState<AppUser?>,
    phoneAuthRepository: PhoneAuthRepository
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

            ProfileScreen(navController, userName = username, email = email, profilePictureUrl = profilePictureUrl)
        }
        composable(
            "edit_profile/{username}/{email}/{profilePictureUrl}",
            arguments = listOf(
                navArgument("username") { type = NavType.StringType; defaultValue = "" },
                navArgument("email") { type = NavType.StringType; defaultValue = "" },
                navArgument("profilePictureUrl") { type = NavType.StringType; defaultValue = "" }
            )
        ) { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: ""
            val email = backStackEntry.arguments?.getString("email") ?: ""
            val profilePictureUrlEncoded = backStackEntry.arguments?.getString("profilePictureUrl") ?: ""
            val profilePictureUrl = URLDecoder.decode(profilePictureUrlEncoded, StandardCharsets.UTF_8.toString())

            EditProfileScreen(navController, userName = username, email = email, profilePictureUrl = profilePictureUrl, authRepository,userDetailsState)
        }
        composable("contact") { KontakScreen(navController) }
        composable("kebijakan") { KebijakanScreen(navController) }



        composable("register") { RegisterScreen(navController, authRepository, googleSignInLauncher) }
        composable("forget") { ForgotScreen(navController) }
        composable("otp") {
            PhoneScreen(
                navController = navController,
                phoneAuthRepository = phoneAuthRepository // Pass your repository
            )
        }


    }
}
