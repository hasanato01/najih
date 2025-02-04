package com.najih.android.api.auth

import android.content.Context
import android.util.Log
import com.najih.android.api.globalData.BASE_URL
import com.najih.android.api.globalData.RE_FETCH_USER_INFO
import com.najih.android.dataClasses.CheckJWTResponse
import com.najih.android.util.GlobalFunctions
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import kotlinx.serialization.json.Json


suspend fun checkJWT(
    httpClient: HttpClient,
    context: Context // Add context to retrieve token
): CheckJWTResponse {
    val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
    }

    val requestUrl = "$BASE_URL$RE_FETCH_USER_INFO"
    val token = GlobalFunctions.getUserInfo(context).token

    Log.d("ApiClient", "Making GET request to URL: $requestUrl")
    Log.d("ApiClient", "Authorization Token: Bearer $token")

    return try {
        val response: HttpResponse = httpClient.get(requestUrl) {
            contentType(ContentType.Application.Json)
            headers {
                append(HttpHeaders.Authorization, "Bearer $token")
            }
        }

        if (response.status == HttpStatusCode.OK) {
            val responseBody = response.bodyAsText()
            Log.d("ApiClient", "Response Body: $responseBody")

            val checkJWTResponse = json.decodeFromString<CheckJWTResponse>(responseBody)
            // Optionally save the updated user info locally if needed
            checkJWTResponse.user?.let {
                GlobalFunctions.saveUserInfo(
                    context,
                    token,
                    it.id,
                    it.name,
                    it.username,
                    it.purchasedLessons,
                    it.recorderLessonsIds,
                    it.teachersLessonsIds
                )
            }
            checkJWTResponse
        } else {
            // Handle non-OK responses
            val errorBody = response.bodyAsText()
            Log.e("ApiClient", "CheckJWT failed with status ${response.status}\nResponse: $errorBody")

            json.decodeFromString<CheckJWTResponse>(errorBody)
        }
    } catch (e: Exception) {
        Log.e("ApiClient", "Error during CheckJWT: ${e.message}", e)
        CheckJWTResponse(
            status = "JWT check failed",
            accessToken = null,
            user = null,

        )
    }
}
