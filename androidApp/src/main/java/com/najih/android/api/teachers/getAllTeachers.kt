package com.najih.android.api.teachers

import android.content.Context
import android.util.Log
import com.najih.android.api.globalData.BASE_URL
import com.najih.android.api.globalData.GET_ACCEPTED_TEACHERS
import com.najih.android.dataClasses.Teacher
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
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

suspend fun getAllTeachers(
    httpClient: HttpClient,
    context: Context // Add context to retrieve token
): List<Teacher> {
    val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
    }

    val requestUrl = "${BASE_URL}${GET_ACCEPTED_TEACHERS}"
    // Retrieve the JWT token using the getUserInfo function
    val token = GlobalFunctions.getUserInfo(context).token
    val user = GlobalFunctions.getUserInfo(context).userName

    // Log the request details
    Log.d("ApiClient", "Making GET request to URL: $requestUrl")
    Log.d("ApiClient", "Content-Type: ${ContentType.Application.Json}")

    return try {
        val response: HttpResponse = httpClient.get(requestUrl) {
            contentType(ContentType.Application.Json)
            headers {
                append(HttpHeaders.Authorization, "Bearer $token")
            }
        }

        when (response.status) {
            HttpStatusCode.OK -> {
                val responseBody = response.bodyAsText()
                Log.d("ApiClient", "Response Body: $responseBody")
                json.decodeFromString(responseBody)
            }
            else -> {
                val errorMessage = "Get Teachers failed with status ${response.status}\nResponse: ${response.bodyAsText()}"
                Log.e("ApiClient", errorMessage)
                throw Exception(errorMessage)
            }
        }
    } catch (e: Exception) {
        Log.e("ApiClient", "Error during getting teachers: ${e.message}", e)
        throw e
    }
}
