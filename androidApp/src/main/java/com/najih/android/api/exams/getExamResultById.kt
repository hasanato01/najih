package com.najih.android.api.exams

import android.content.Context
import android.util.Log
import com.najih.android.api.globalData.BASE_URL
import com.najih.android.api.globalData.SUBMIT_EXAM_ENDPOINT
import com.najih.android.api.globalData.USER_EXAMS_ENDPOINT
import com.najih.android.dataClasses.SubmitExamRequest
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

suspend fun getExamResultById(httpClient: HttpClient, context: Context, examResultID : String) : SubmitExamRequest {

    val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
    }
    val requestUrl = "$BASE_URL$SUBMIT_EXAM_ENDPOINT/$examResultID"
    val token = GlobalFunctions.getUserInfo(context).token
    Log.d("ApiClient", requestUrl)
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
                json.decodeFromString(responseBody)  // Deserialize into a list of Exam objects
            }
            else -> {
                val errorMessage = "Get  Exam with Id : $examResultID failed with status ${response.status}\nResponse: ${response.bodyAsText()}"
                Log.e("ApiClient", errorMessage)
                throw Exception(errorMessage)
            }
        }
    } catch (e: Exception) {
        Log.e("ApiClient", "Error during getting user exam : $examResultID: ${e.message}", e)
        throw e
    }
}