import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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


    fun logout() {
        firebaseAuth.signOut()
    }
}
