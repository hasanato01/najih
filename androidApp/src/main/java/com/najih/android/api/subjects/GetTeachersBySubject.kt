package com.najih.android.api.subjects

import android.util.Log
import com.najih.android.api.globalData.BASE_URL
import com.najih.android.api.globalData.STREAMS_TEACHERS_ENDPOINT
import com.najih.android.dataClasses.StreamsSubjectWithTeachers
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import kotlinx.serialization.json.Json

suspend fun GetTeachersBySubject  (
    httpClient: HttpClient,
    subjectId : String
) : StreamsSubjectWithTeachers {
    val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
    }
    val requestUrl = "${BASE_URL}${STREAMS_TEACHERS_ENDPOINT}${subjectId}"
    // Log the request details

    // Log the request details
    Log.d("ApiClient", "Making GET request to URL: $requestUrl")
    Log.d("ApiClient", "Content-Type: ${ContentType.Application.Json}")

    return try {
        val response: HttpResponse = httpClient.get(requestUrl) {
            contentType(ContentType.Application.Json)
        }

        when (response.status) {
            HttpStatusCode.OK -> {
                val responseBody = response.bodyAsText()
                Log.d("ApiClient", "Response Body: $responseBody")
                json.decodeFromString<StreamsSubjectWithTeachers>(responseBody)  // Deserialize into a list
            }
            else -> {
                val errorMessage = "get Subject data failed with status ${response.status}\nResponse: ${response.bodyAsText()}"
                Log.e("ApiClient", errorMessage)
                throw Exception(errorMessage)
            }
        }
    } catch (e: Exception) {
        Log.e("ApiClient", "Error during getting subject data: ${e.message}", e)
        throw e
    }

}