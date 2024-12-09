import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.mawar.bsecure.model.AppUser
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.cancellation.CancellationException
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class AuthRepository {

    private val tag = "AuthRepository: "
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance() // Initialize Firestore

    fun isLoggedIn(): Boolean {
        return firebaseAuth.currentUser != null
    }

    suspend fun register(
        email: String,
        username: String,
        password: String
    ): Boolean {
        try {
            val result = suspendCoroutine<Boolean> { continuation ->
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener { authResult ->
                        println(tag + "register success")
                        val user = authResult.user
                        val defaultProfilePictureUrl = "https://ia601308.us.archive.org/8/items/whatsapp-smiling-guy-i-accidentally-made/whatsapp%20old%20generic%20person.jpeg"
                        // After registration, add user info to Firestore
                        user?.let {
                            val userData = hashMapOf(
                                "uid" to it.uid,
                                "email" to email,
                                "username" to username,
                                "password" to password,
                                "profilePictureUrl" to defaultProfilePictureUrl

                            )
                            firestore.collection("users").document(it.uid)
                                .set(userData)
                                .addOnSuccessListener {
                                    println(tag + "User data added to Firestore")
                                    continuation.resume(true)
                                }
                                .addOnFailureListener { e ->
                                    println(tag + "Failed to add user data to Firestore: ${e.message}")
                                    continuation.resume(false)
                                }
                        } ?: continuation.resume(false)
                    }
                    .addOnFailureListener {
                        println(tag + "register failure ${it.message}")
                        continuation.resume(false)
                    }
            }
            return result

        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
            println(tag + "register exception ${e.message}")
            return false
        }
    }

    suspend fun login(
        email: String, password: String
    ): Boolean {
        try {
            val result = suspendCoroutine<Boolean> { continuation ->
                firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener {
                        println(tag + "login success")
                        continuation.resume(true)
                    }
                    .addOnFailureListener {
                        println(tag + "login failure ${it.message}")
                        continuation.resume(false)
                    }
            }
            return result

        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
            println(tag + "login exception ${e.message}")
            return false
        }
    }
    suspend fun updateProfile(
        name: String,
        email: String,
        profilePictureUrl: String,
        phoneNumber: String
    ): Boolean {
        val uid = firebaseAuth.currentUser?.uid ?: return false
        return try {
            val userUpdates = mapOf(
                "username" to name,
                "email" to email,
                "profilePictureUrl" to profilePictureUrl, // Include profile picture URL
                "phoneNumber" to phoneNumber
            )
            firestore.collection("users").document(uid).update(userUpdates).await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }


    suspend fun getUserProfile(): AppUser? {
        val uid = firebaseAuth.currentUser?.uid ?: return null
        return try {
            val document = firestore.collection("users").document(uid).get().await()
            if (document.exists()) {
                val data = document.data
                AppUser(
                    uid = uid,
                    email = data?.get("email") as String,
                    username = data["username"] as String,
                    profilePictureUrl = data["profilePictureUrl"] as String,
                    phoneNumber = data["phoneNumber"] as String

                )
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun logout() {
        firebaseAuth.signOut()
    }
}
