package com.mawar.bsecure.navigation

import AuthRepository
import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mawar.bsecure.login.LoginScreen
import com.mawar.bsecure.login.RegisterScreen
import com.mawar.bsecure.model.AppUser
import com.mawar.bsecure.model.LoginModel
import com.mawar.bsecure.model.post.Post
import com.mawar.bsecure.repository.CommunityRepository
import com.mawar.bsecure.ui.view.login.ForgotScreen
import com.mawar.bsecure.ui.view.profile.EditProfileScreen
import com.mawar.bsecure.ui.view.profile.KontakScreen
import com.mawar.bsecure.ui.view.profile.PhoneScreen
import com.mawar.bsecure.ui.view.profile.ProfileScreen
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import com.mawar.bsecure.repository.PhoneAuthRepository
import com.mawar.bsecure.ui.view.screen.SOSScreen
import com.mawar.bsecure.ui.view.community.CommunityPostDetailScreen
import com.mawar.bsecure.ui.view.community.CommunityScreen
import com.mawar.bsecure.ui.viewModel.community.CommunityViewModelFactory
import com.mawar.bsecure.ui.view.community.AddCommunity
import com.mawar.bsecure.ui.view.profile.KebijakanScreen
import com.mawar.bsecure.ui.view.screen.FakeCallScreen
import com.mawar.bsecure.ui.view.screen.IncomingCallScreen
import com.mawar.bsecure.ui.view.screen.LocationScreen
import com.mawar.bsecure.ui.viewModel.community.CommunityViewModel
import com.mawar.bsecure.ui.viewModel.fakeCall.TimerViewModel
import com.mawar.bsecure.ui.viewModel.location.LocationViewModel

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
        val uid = appUser.uid
        val username = appUser.username
        val email = appUser.email
        val encodedProfilePictureUrl = URLEncoder.encode(appUser.profilePictureUrl, StandardCharsets.UTF_8.toString())

        if (username.isNotEmpty() && email.isNotEmpty()) {
            println("Navigating to profile with username: $username, email: $email")
            navController.navigate("sos/$username/$email/$encodedProfilePictureUrl/$uid")
        }
        userDetailsState.value = null // Reset the state to avoid repeated navigation
    }

    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen(navController, loginModel) }
        composable(
            "sos/{username}/{email}/{profilePictureUrl}/{uid}",
            arguments = listOf(
                navArgument("uid") { type = NavType.StringType; defaultValue = "tes" },
                navArgument("username") { type = NavType.StringType; defaultValue = "" },
                navArgument("email") { type = NavType.StringType; defaultValue = "" },
                navArgument("profilePictureUrl") { type = NavType.StringType; defaultValue = "" }
            )
        ) { backStackEntry ->
            val uid = backStackEntry.arguments?.getString("uid") ?: ""
            val username = backStackEntry.arguments?.getString("username") ?: ""
            val email = backStackEntry.arguments?.getString("email") ?: ""
            val profilePictureUrlEncoded = backStackEntry.arguments?.getString("profilePictureUrl") ?: ""

            // Decode the profile picture URL
            val profilePictureUrl = URLDecoder.decode(profilePictureUrlEncoded, StandardCharsets.UTF_8.toString())

            SOSScreen(navController, username = username, email = email, profilePictureUrl = profilePictureUrl, uid=uid)
        }
        composable(
            "fakecall/{username}/{email}/{profilePictureUrl}/{uid}",
            arguments = listOf(
                navArgument("uid") { type = NavType.StringType; defaultValue = "tes" },
                navArgument("username") { type = NavType.StringType; defaultValue = "" },
                navArgument("email") { type = NavType.StringType; defaultValue = "" },
                navArgument("profilePictureUrl") { type = NavType.StringType; defaultValue = "" }
            )
        ) { backStackEntry ->
            val uid = backStackEntry.arguments?.getString("uid") ?: ""
            val username = backStackEntry.arguments?.getString("username") ?: ""
            val email = backStackEntry.arguments?.getString("email") ?: ""
            val profilePictureUrlEncoded = backStackEntry.arguments?.getString("profilePictureUrl") ?: ""

            // Decode the profile picture URL
            val profilePictureUrl = URLDecoder.decode(profilePictureUrlEncoded, StandardCharsets.UTF_8.toString())

            FakeCallScreen(navController, username = username, email = email, profilePictureUrl = profilePictureUrl, uid=uid, timerViewModel = TimerViewModel())
        }
        composable(
            "location/{username}/{email}/{profilePictureUrl}/{uid}",
            arguments = listOf(
                navArgument("uid") { type = NavType.StringType; defaultValue = "tes" },
                navArgument("username") { type = NavType.StringType; defaultValue = "" },
                navArgument("email") { type = NavType.StringType; defaultValue = "" },
                navArgument("profilePictureUrl") { type = NavType.StringType; defaultValue = "" }
            )
        ) { backStackEntry ->
            val uid = backStackEntry.arguments?.getString("uid") ?: ""
            val username = backStackEntry.arguments?.getString("username") ?: ""
            val email = backStackEntry.arguments?.getString("email") ?: ""
            val profilePictureUrlEncoded = backStackEntry.arguments?.getString("profilePictureUrl") ?: ""

            // Decode the profile picture URL
            val profilePictureUrl = URLDecoder.decode(profilePictureUrlEncoded, StandardCharsets.UTF_8.toString())

            LocationScreen(navController, username = username, email = email, profilePictureUrl = profilePictureUrl, uid=uid
            )
        }

        composable(
            route = "incoming_call/{contactName}",
            arguments = listOf(navArgument("contactName") { defaultValue = "Unknown" })
        ) { backStackEntry ->
            val contactName = backStackEntry.arguments?.getString("contactName") ?: "Unknown"
            IncomingCallScreen(contactName = contactName,navController)
        }

        composable(
            "profile/{username}/{email}/{profilePictureUrl}/{uid}",
            arguments = listOf(
                navArgument("uid") { type = NavType.StringType; defaultValue = "tes" },
                navArgument("username") { type = NavType.StringType; defaultValue = "" },
                navArgument("email") { type = NavType.StringType; defaultValue = "" },
                navArgument("profilePictureUrl") { type = NavType.StringType; defaultValue = "" }
            )
        ) { backStackEntry ->
            val uid = backStackEntry.arguments?.getString("uid") ?: ""
            val username = backStackEntry.arguments?.getString("username") ?: ""
            val email = backStackEntry.arguments?.getString("email") ?: ""
            val profilePictureUrlEncoded = backStackEntry.arguments?.getString("profilePictureUrl") ?: ""

            // Decode the profile picture URL
            val profilePictureUrl = URLDecoder.decode(profilePictureUrlEncoded, StandardCharsets.UTF_8.toString())

            ProfileScreen(navController, username = username, email = email, profilePictureUrl = profilePictureUrl, uid=uid)
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

        composable("community/{uid}",
            arguments = listOf(
                navArgument("uid") { type = NavType.StringType; defaultValue = "" }
            )) {backStackEntry ->
            val uid = backStackEntry.arguments?.getString("uid") ?: ""

            Log.d("AppNavigation", "Navigating to community screen with username: $uid")
            MainScreen(navController, uid = uid) }

        composable("addCommunity/{uid}",
            arguments = listOf(
                navArgument("uid") { type = NavType.StringType; defaultValue = "" }
            )
        ) { backStackEntry ->
            val uid = backStackEntry.arguments?.getString("uid") ?: ""
            addCommunityNav(navController, uid)
        }

//        composable("addCommunity"){
//            val uid = userDetailsState.value?.uid ?: ""
//            addCommunityNav(uid)
//        }
    }

}

@Composable
fun MainScreen(navController: NavController, uid: String) {
    var selectedPost by remember { mutableStateOf<Post?>(null) }
    var isCommentClicked by remember { mutableStateOf(false) }
    val communityViewModel: CommunityViewModel = viewModel(factory = CommunityViewModelFactory(CommunityRepository()))
    if (selectedPost == null) {
        CommunityScreen(
            onPostClick = { post ->
                selectedPost = post
                isCommentClicked = false // General navigation
            },
            onCommentClick = { post ->
                selectedPost = post
                isCommentClicked = true // Navigate to the comment section
            },
            navApp = navController,
            uid = uid
        )
    } else {
        val userData = communityViewModel.userCache[selectedPost!!.uid] // Get user data for the selected post's UID
        CommunityPostDetailScreen(
            onPostClick = { post ->
                selectedPost = post
                isCommentClicked = false // General navigation
            },
            navController = navController,
            uid = uid,
            post = selectedPost!!,
            userData = userData,
            onCommentClick = { post ->
                selectedPost = post
                isCommentClicked = true // Navigate to the comment section
            },
        )
    }
}

@Composable
fun addCommunityNav(navController: NavController, uid: String) {
    AddCommunity(uid= uid, navController = navController, repository = CommunityRepository())
}
