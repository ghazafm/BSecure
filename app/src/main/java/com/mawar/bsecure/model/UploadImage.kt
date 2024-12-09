import android.content.Context
import android.net.Uri
import android.util.Log
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import org.json.JSONObject
import java.io.File
import java.io.IOException
import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.InputStream
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

suspend fun uploadImage(inputStream: InputStream, clientId: String = "cf09546cf82e9c7"): String =
    suspendCancellableCoroutine { continuation ->
        val client = OkHttpClient()

        // Create RequestBody from InputStream
        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("image", "upload.jpg", inputStream.readBytes().toRequestBody("image/*".toMediaTypeOrNull()))
            .build()

        val request = Request.Builder()
            .url("https://api.imgur.com/3/image")
            .addHeader("Authorization", "Client-ID $clientId")
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("uploadImage", "Upload failed: ${e.message}")
                if (continuation.isActive) {
                    continuation.resumeWithException(e)
                }
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val responseBody = response.body?.string()
                    Log.d("uploadImage", "Response: $responseBody")
                    val jsonResponse = JSONObject(responseBody)
                    val imageUrl = jsonResponse.getJSONObject("data").getString("link")
                    continuation.resume(imageUrl)
                } else {
                    Log.e("uploadImage", "Failed to upload image: ${response.message}")
                    continuation.resumeWithException(
                        Exception("Failed to upload image: ${response.message}")
                    )
                }
            }
        })
    }


fun getInputStreamFromUri(context: Context, uri: Uri): InputStream? {
    return try {
        context.contentResolver.openInputStream(uri)
    } catch (e: Exception) {
        Log.e("getInputStreamFromUri", "Error accessing input stream: ${e.message}")
        null
    }
}
