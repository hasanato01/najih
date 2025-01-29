package com.najih.android.api.auth


import android.util.Log
import com.najih.android.api.globalData.BASE_URL
import com.najih.android.api.globalData.VERIFY_EMAIL
import com.najih.android.dataClasses.VerificationRequest
import com.najih.android.dataClasses.VerificationResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import kotlinx.serialization.json.Json

suspend fun VerifyCode(
    httpClient: HttpClient,
    email: String,
    verificationCode: String
): VerificationResponse {
    val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
    }

    val requestUrl = "$BASE_URL$VERIFY_EMAIL"
    val requestBody = VerificationRequest(email, verificationCode)
    Log.d("ApiClient", "Making POST request to URL: $requestUrl with requestBody: $requestBody")

    return try {
        val response = httpClient.post(requestUrl) {
            contentType(ContentType.Application.Json)
            setBody(requestBody)
        }

        val responseBody = response.bodyAsText()
        Log.d("ApiClient", "Response Body: $responseBody")

        when (response.status) {
            HttpStatusCode.OK -> json.decodeFromString<VerificationResponse>(responseBody)
            else -> {
                val errorMessage = "Verification failed with status ${response.status}\nResponse: $responseBody"
                Log.e("ApiClient", errorMessage)
                throw Exception(errorMessage)
            }
        }
    } catch (e: Exception) {
        Log.e("ApiClient", "Error during verification: ${e.message}", e)
        throw e
    }
}
