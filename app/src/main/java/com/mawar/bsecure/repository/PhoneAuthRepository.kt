package com.mawar.bsecure.repository

import android.util.Log
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import android.app.Activity
import java.util.concurrent.TimeUnit

class PhoneAuthRepository {

    val auth: FirebaseAuth = FirebaseAuth.getInstance()

    /**
     * Mengirim OTP ke nomor telepon.
     */
    fun sendOtp(
        phoneNumber: String,
        activity: Activity,
        callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    ) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(callbacks)
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    /**
     * Memverifikasi OTP tanpa login.
     */
    fun verifyOtp(
        verificationId: String,
        otp: String,
        onSuccess: (PhoneAuthCredential) -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            val credential = PhoneAuthProvider.getCredential(verificationId, otp)
            onSuccess(credential)
        } catch (e: Exception) {
            onError(e.message ?: "Verifikasi OTP gagal")
        }
    }

    /**
     * Menyetel nomor telepon pada akun pengguna yang sudah login.
     */
    fun updatePhoneNumber(
        credential: PhoneAuthCredential,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            currentUser.updatePhoneNumber(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        onSuccess()
                    } else {
                        val errorMessage = task.exception?.message ?: "Gagal menyetel nomor telepon"
                        Log.e(TAG, "updatePhoneNumber: $errorMessage")
                        onError(errorMessage)
                    }
                }
        } else {
            onError("Pengguna tidak ditemukan atau belum login")
        }
    }

    companion object {
        private const val TAG = "PhoneAuthRepository"
    }
}
