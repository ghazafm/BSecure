package com.mawar.bsecure.model

import android.content.Context
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

fun sendPasswordResetEmail(
    context: Context,
    email: String,
    onSuccess: () -> Unit,
    onFailure: (Exception) -> Unit,
    updateFirestoreAfterReset: Boolean = false // Optional parameter to control Firestore update
) {
    val auth = FirebaseAuth.getInstance()
    val firestore = FirebaseFirestore.getInstance()

    firestore.collection("users")
        .whereEqualTo("email", email)
        .get()
        .addOnSuccessListener { documents ->
            if (documents.isEmpty) {
                // Email does not exist in Firestore
                Toast.makeText(context, "Email tidak ditemukan", Toast.LENGTH_SHORT).show()
            } else {
                // Email exists in Firestore, proceed to send password reset email
                auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            onSuccess()

                            // Optionally update Firestore
                            if (updateFirestoreAfterReset) {
                                val documentId = documents.documents[0].id
                                firestore.collection("users").document(documentId)
                                    .update("password", "new_password_placeholder") // Placeholder for the new password
                                    .addOnSuccessListener {
                                        Toast.makeText(context, "Firestore updated with new placeholder password.", Toast.LENGTH_SHORT).show()
                                    }
                                    .addOnFailureListener { e ->
                                        Toast.makeText(context, "Failed to update Firestore: ${e.message}", Toast.LENGTH_SHORT).show()
                                    }
                            }
                        } else {
                            task.exception?.let { onFailure(it) }
                        }
                    }
            }
        }
        .addOnFailureListener { exception ->
            onFailure(exception)
        }
}