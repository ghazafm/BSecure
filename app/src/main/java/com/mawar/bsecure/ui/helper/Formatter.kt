package com.mawar.bsecure.ui.helper

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Formatter {
    /**
     * Format the timestamp into a human-readable string.
     * @param timestamp Long value representing the time in milliseconds.
     * @return Formatted string of timestamp.
     */
    fun formatTimestamp(timestamp: Long): String {
        return try {
            val sdf = SimpleDateFormat("HH:mm, dd MMM yyyy", Locale.getDefault())
            val date = Date(timestamp)
            sdf.format(date)
        } catch (e: Exception) {
            "Invalid Date"
        }
    }
}