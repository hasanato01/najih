package com.najih.android.api.purchasedStreams



import android.content.Context
import android.util.Log
import com.najih.android.api.globalData.BASE_URL
import com.najih.android.api.globalData.GET_MY_RECORDED_LESSONS
import com.najih.android.api.globalData.GET_MY_STREAMS
import com.najih.android.dataClasses.Exam
import com.najih.android.dataClasses.MyRecorderLessonsResponse
import com.najih.android.dataClasses.Streams
import com.najih.android.util.GlobalFunctions
import io.ktor.client.HttpClient
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.http.HttpHeaders
import kotlinx.serialization.json.Json

suspend fun GetMyStreams(
    httpClient: HttpClient,
    context: Context
):List <Streams>{
    val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
    }

    val requestUrl = "${BASE_URL}${GET_MY_STREAMS}"

    // Retrieve the JWT token using the getUserInfo function
    val token = GlobalFunctions.getUserInfo(context).token
    val user = GlobalFunctions.getUserInfo(context).userName
    val purchasedLessonsIDS = GlobalFunctions.getUserInfo(context).teachersLessonsIds

    // Log the request details
    Log.d("ApiClient", "Making GET request to URL: $requestUrl")
    Log.d("ApiClient", "token: $token for $user")
    Log.d("ApiClient", "purchasedLessonsIDS: $purchasedLessonsIDS")

    return try {
        val response: HttpResponse = httpClient.post(requestUrl) {
            contentType(ContentType.Application.Json)
            setBody(purchasedLessonsIDS)
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
                val errorMessage = "Get User Streams failed with status ${response.status}\nResponse: ${response.bodyAsText()}"
                Log.e("ApiClient", errorMessage)
                throw Exception(errorMessage)
            }
        }
    } catch (e: Exception) {
        Log.e("ApiClient", "Error during getting User Streams: ${e.message}", e)
        throw e
    }
}
