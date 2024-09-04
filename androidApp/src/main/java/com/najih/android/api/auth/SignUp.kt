package com.najih.android.api.auth

import android.content.Context
import android.util.Log
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
    name: String,
    phone: String,
    email: String,
    password: String
): SignUpResponse {
    val role = "student"
    val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
    }
    val endPoint = "users/signup"
    val requestBody = json.encodeToString(SignUpRequest(name, phone, email, password, role))
    Log.d("ApiClient", "Request Body: $requestBody")

    return try {
        val response: HttpResponse = httpClient.post("https://nserver.najih1.com/${endPoint}") {
            contentType(ContentType.Application.Json)
            setBody(SignUpRequest(name, phone, email, password, role))
        }

        when (response.status) {
            HttpStatusCode.OK -> {
                val responseBody = response.bodyAsText()
                Log.d("ApiClient", "Response Body: $responseBody")
                json.decodeFromString(responseBody)
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