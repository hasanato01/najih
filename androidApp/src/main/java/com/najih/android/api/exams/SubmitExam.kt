package com.najih.android.api.exams

import LanguageContent
import android.util.Log
import com.najih.android.api.globalData.BASE_URL
import com.najih.android.api.globalData.SUBMIT_EXAM_ENDPOINT
import com.najih.android.dataClasses.ExamResults
import com.najih.android.dataClasses.SubmitExamRequest
import com.najih.android.dataClasses.SubmittedExamResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import kotlinx.serialization.json.Json

suspend fun SubmitExam(
   httpClient: HttpClient,
    userId:String,
    examId:String,
    resultsArray:ExamResults,
   correctAnswersCount: Int,
   examName: LanguageContent,
   totalQuestions: Int,
    submittedAt: String) : SubmittedExamResponse {
    val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
    }
    val requestUrl="${BASE_URL}${SUBMIT_EXAM_ENDPOINT}"
    return try {
        val response: HttpResponse = httpClient.post(requestUrl) {
            contentType(ContentType.Application.Json)
            setBody(SubmitExamRequest(examId, userId, resultsArray, correctAnswersCount, examName, totalQuestions, submittedAt))
        }
        when (response.status) {
            HttpStatusCode.OK -> {
                val responseBody = response.bodyAsText()
                Log.d("ApiClient", "Response Body: $responseBody")
                json.decodeFromString<SubmittedExamResponse>(responseBody)
            }
            else -> {
                val errorMessage =
                    "Submit exam failed with status ${response.status}\nResponse: ${response.bodyAsText()}"
                Log.e("ApiClient", errorMessage)
                throw Exception(errorMessage)
            }
        }
    } catch (e: Exception) {
        Log.e("ApiClient", "Error during submitExam: ${e.message}", e)
        throw e
    }
}