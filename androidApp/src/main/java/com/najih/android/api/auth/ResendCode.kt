package com.najih.android.api.auth

import android.util.Log
import com.najih.android.api.globalData.BASE_URL
import com.najih.android.api.globalData.RESEND_CODE
import com.najih.android.dataClasses.User
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.json.Json

suspend fun ResendCode(httpClient: HttpClient, email: String): User {
    val json = Json { ignoreUnknownKeys = true }

    val requestUrl = "$BASE_URL$RESEND_CODE/$email"
    Log.d("ApiClient", "Making GET request to URL: $requestUrl")

    return try {
        val response = httpClient.get(requestUrl)
        val responseBody = response.bodyAsText()
        Log.d("ApiClient", "Response Body: $responseBody")

        when (response.status) {
            HttpStatusCode.OK -> json.decodeFromString<User>(responseBody)
            else -> {
                val errorMessage = "Request failed with status ${response.status}\nResponse: $responseBody"
                Log.e("ApiClient", errorMessage)
                throw Exception(errorMessage)
            }
        }
    } catch (e: Exception) {
        Log.e("ApiClient", "Error during request: ${e.message}", e)
        throw e
    }
}
