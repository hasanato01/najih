package com.najih.android.api.auth

import android.content.Context
import android.util.Log
import com.najih.android.api.globalData.BASE_URL
import com.najih.android.api.globalData.SIGNING_ENDPOINT
import com.najih.android.api.globalData.SIGNUP_ENDPOINT
import com.najih.android.dataClasses.SignUpRequest
import com.najih.android.dataClasses.SignUpResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import kotlinx.serialization.encodeToString
import  kotlinx.serialization.json.Json

suspend fun signUp(
    httpClient: HttpClient,
    context: Context,
    signUpRequest: SignUpRequest
): SignUpResponse {
    val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
    }

    val endPoint = "users/signup" // Adjust the endpoint as per your API

    Log.d("ApiClient", "Request Body: ${json.encodeToString(signUpRequest)}")

    return try {
        // Directly pass the object to `setBody` to avoid double serialization
        val response: HttpResponse = httpClient.post("$BASE_URL$endPoint") {
            contentType(ContentType.Application.Json)
            setBody(signUpRequest) // Pass the data object directly
        }

        when (response.status) {
            HttpStatusCode.OK -> {
                val responseBody = response.bodyAsText()
                Log.d("ApiClient", "Response Body: $responseBody")
                json.decodeFromString(responseBody) // Decode response to your response model
            }
            else -> {
                val errorMessage = "Sign-up failed with status ${response.status}\nResponse: ${response.bodyAsText()}"
                Log.e("ApiClient", errorMessage)
                throw Exception(errorMessage)
            }
        }
    } catch (e: Exception) {
        Log.e("ApiClient", "Error during signUp: ${e.message}", e)
        throw e
    }
}