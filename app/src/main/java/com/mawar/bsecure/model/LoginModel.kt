// LoginModel.kt
package com.mawar.bsecure.model

import AuthRepository
import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.runtime.Composable
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.mawar.bsecure.R
import kotlinx.coroutines.tasks.await

class LoginModel(
    private val db: FirebaseFirestore,
    private val activity: Activity,
    private val launcher: ActivityResultLauncher<Intent>,
    private val authRepository: AuthRepository
) {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var googleSignInClient: GoogleSignInClient

    init {
        googleSignInClient = GoogleSignIn.getClient(
            activity,
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(activity.getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        )
    }
    suspend fun login(email: String, password: String): AppUser? {
        return try {
            val authResult = auth.signInWithEmailAndPassword(email, password).await()
            val uid = authResult.user?.uid

            if (uid != null) {
                // Fetch user data from Firestore using uid
                val document = db.collection("users").document(uid).get().await()
                if (document.exists()) {
                    // Parse Firestore document into User object
                    document.toObject(AppUser::class.java)
                } else {
                    null
                }
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun signInWithGoogle() {
        googleSignInClient.signOut().addOnCompleteListener {
            val signInIntent = googleSignInClient.signInIntent
            launcher.launch(signInIntent)
        }
    }

    fun signInWithGoogle(onSuccess: (AppUser) -> Unit, onFailure: (Exception) -> Unit) {
        googleSignInClient.signOut().addOnCompleteListener {
            val signInIntent = googleSignInClient.signInIntent
            launcher.launch(signInIntent)
        }
    }

    fun handleGoogleSignInResult(
        data: Intent?,
        onSuccess: (AppUser) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            val account = task.getResult(ApiException::class.java)
            firebaseAuthWithGoogle(account, onSuccess, onFailure)
        } catch (e: ApiException) {
            onFailure(e)
            Log.e("GoogleSignIn", "Google sign-in failed", e)
        }
    }

    private fun firebaseAuthWithGoogle(
        account: GoogleSignInAccount,
        onSuccess: (AppUser) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    user?.let {
                        // Store user data in Firestore
                        storeUserDataInFirestore(
                            uid = it.uid,
                            email = it.email ?: "",
                            displayName = it.displayName ?: "",
                            profilePictureUrl = it.photoUrl?.toString() ?: ""
                        )

                        // Retrieve the username from Firestore
                        firestore.collection("users").document(it.uid).get()
                            .addOnSuccessListener { document ->
                                val appUser = document.toObject(AppUser::class.java)
                                if (appUser != null) {
                                    onSuccess(appUser) // Pass the full AppUser object
                                } else {
                                    onFailure(Exception("User data is missing"))
                                }
                            }
                            .addOnFailureListener { e ->
                                onFailure(e)
                                Log.e("Firestore", "Failed to retrieve user data", e)
                            }
                    }
                } else {
                    onFailure(task.exception ?: Exception("Unknown error"))
                    Log.e("GoogleSignIn", "signInWithCredential:failure", task.exception)
                }
            }
    }




    private fun storeUserDataInFirestore(
        uid: String,
        email: String,
        displayName: String,
        profilePictureUrl: String
    ) {
        val userData = hashMapOf(
            "uid" to uid,
            "email" to email,
            "username" to displayName,
            "profilePictureUrl" to profilePictureUrl
        )

        firestore.collection("users").document(uid)
            .set(userData)
            .addOnSuccessListener { Log.d("Firestore", "User data stored successfully.") }
            .addOnFailureListener { e -> Log.e("Firestore", "Error storing user data", e) }
    }}


