package com.mawar.bsecure

import AuthRepository
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore
import com.mawar.bsecure.model.AppUser
import com.mawar.bsecure.model.LoginModel
import com.mawar.bsecure.navigation.AppNavigation
import com.mawar.bsecure.repository.PhoneAuthRepository
import com.mawar.bsecure.ui.theme.BSecureTheme
import com.mawar.bsecure.ui.view.community.CommunityPostDetailScreen
import com.mawar.bsecure.ui.view.community.CommunityScreen
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

//@Composable
//fun MainScreen(navController: NavController) {
//    var selectedPost by remember { mutableStateOf<Post?>(null) }
//    if (selectedPost == null) {
//        CommunityScreen(onPostClick = { post -> selectedPost = post },navController)
//    } else {
//        CommunityPostDetailScreen(
//            post = selectedPost!!,
//            comments = listOf(
//                Post("Lukas Awan", "@LukasAwan", "Aplikasi ini sangat menarik dan sangat membantu!", "28s", R.drawable.user),
//                Post("Lukas Awan", "@LukasAwan", "Aplikasi ini memiliki fitur yang sangat berguna!", "18s", R.drawable.user)
//            )
//        )
//    }
//}



