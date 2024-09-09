package com.najih.android.api.auth


import android.content.Context
import android.util.Log
import com.najih.android.dataClasses.SignInRequest
import com.najih.android.dataClasses.SignInResponse
import com.najih.android.util.GlobalFunctions
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import kotlinx.serialization.encodeToString
import  kotlinx.serialization.json.Json


suspend fun signIn(
    httpClient: HttpClient,
    context: Context,
    email: String,
    password: String
): SignInResponse {
    val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
    }
    val endPoint = "users/login"
    val requestBody = json.encodeToString(SignInRequest(email, password))
    Log.d("ApiClient", "Request Body: $requestBody")

    return try {
        val response: HttpResponse = httpClient.post("https://nserver.najih1.com/${endPoint}") {
            contentType(ContentType.Application.Json)
            setBody(SignInRequest(email, password))
        }

        when (response.status) {
            HttpStatusCode.OK -> {
                val signInResponse = response.body<SignInResponse>()
                val accessToken = signInResponse.accessToken
                val userName = signInResponse.user.name
                val userEmail = signInResponse.user.username
                GlobalFunctions.saveUserInfo(context, accessToken , userName , userEmail)
                val responseBody = response.bodyAsText()
                Log.d("ApiClient", "Response Body: $responseBody")
                json.decodeFromString(responseBody)
            }
            else -> {
                val errorMessage = "Sign-in failed with status ${response.status}\nResponse: ${response.bodyAsText()}"
                Log.e("ApiClient", errorMessage)
                throw Exception(errorMessage)
            }
        }
    } catch (e: Exception) {
        Log.e("ApiClient", "Error during signIn: ${e.message}", e)
        throw e
    }
}
