package com.mawar.bsecure.data.emergency

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

object PhoneUtils {
    fun makePhoneCall(context: Context, phoneNumber: String) {
        try {


            val intent = Intent(Intent.ACTION_CALL).apply {
                data = Uri.parse("tel:$phoneNumber")
            }
            val permission = Manifest.permission.CALL_PHONE
            if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED) {
                context.startActivity(intent)
            } else {
                ActivityCompat.requestPermissions(context as Activity, arrayOf(permission), 0)
            }
        }catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "Error making phone call", Toast.LENGTH_LONG).show()
        }
    }
}