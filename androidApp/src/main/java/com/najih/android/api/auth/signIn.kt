package com.najih.android.api.auth


import android.content.Context
import android.util.Log
import com.najih.android.api.globalData.BASE_URL
import com.najih.android.api.globalData.SIGNING_ENDPOINT
import com.najih.android.dataClasses.ErrorDetails
import com.najih.android.dataClasses.SignInRequest
import com.najih.android.dataClasses.SignInResponse
import com.najih.android.util.GlobalFunctions
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

    val requestBody = json.encodeToString(SignInRequest(email, password))
    Log.d("ApiClient", "Request Body: $requestBody")

    return try {
        val response: HttpResponse = httpClient.post("${BASE_URL}${SIGNING_ENDPOINT}") {
            contentType(ContentType.Application.Json)
            setBody(SignInRequest(email, password))
        }

        if (response.status == HttpStatusCode.OK) {
            val responseBody = response.bodyAsText()
            Log.d("ApiClient", "Response Body: $responseBody")

            val signInResponse = json.decodeFromString<SignInResponse>(responseBody)
            signInResponse.user?.let {
                signInResponse.accessToken?.let { it1 ->
                    GlobalFunctions.saveUserInfo(
                        context,
                        it1,
                        it.id,
                        signInResponse.user.name,
                        signInResponse.user.username,
                        signInResponse.user.purchasedLessons,
                        signInResponse.user.recorderLessonsIds,
                        signInResponse.user.teachersLessonsIds
                    )
                }
            }
            signInResponse
        } else {
            // Parse error response and return a SignInResponse object with an error
            val errorBody = response.bodyAsText()
            Log.e("ApiClient", "Sign-in failed with status ${response.status}\nResponse: $errorBody")

            json.decodeFromString<SignInResponse>(errorBody)
        }
    } catch (e: Exception) {
        Log.e("ApiClient", "Error during signIn: ${e.message}", e)
        SignInResponse(
            success = false,
            status = "Sign-in failed",
            accessToken = "",
            user = null,
            err = ErrorDetails(e.message)
        )
    }
}
