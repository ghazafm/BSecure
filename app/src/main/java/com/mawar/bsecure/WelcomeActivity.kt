package com.mawar.bsecure.welcome

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.mawar.bsecure.WelcomeScreen
import com.mawar.bsecure.ui.theme.BSecureTheme

class WelcomeActivity : ComponentActivity() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Default username in case of failure
        var userName = "User"

        // Get current user’s UID
        val uid = auth.currentUser?.uid

        // Check if UID is available
        if (uid != null) {
            // Fetch user document from Firestore
            firestore.collection("users").document(uid).get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        userName = document.getString("username") ?: "User"
                    } else {
                        Log.e("Firestore", "User document does not exist")
                    }
                    // Set content with the retrieved username
                    setContent {
                        BSecureTheme {
                            WelcomeScreen(userName = userName)
                        }
                    }
                }
                .addOnFailureListener { e ->
                    Log.e("Firestore", "Failed to retrieve user data", e)
                    // Set content with default username in case of failure
                    setContent {
                        BSecureTheme {
                            WelcomeScreen(userName = userName)
                        }
                    }
                }
        } else {
            // Set content with default username if UID is null
            setContent {
                BSecureTheme {
                    WelcomeScreen(userName = userName)
                }
            }
        }
    }
}
