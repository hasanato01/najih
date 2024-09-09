package com.najih.android.api.contactUsForm


import android.util.Log
import com.najih.android.dataClasses.ContactUsRequest
import com.najih.android.dataClasses.ContactUsResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

suspend fun postContactForm(
    httpClient: HttpClient,
    name: String,
    email: String,
    phoneNumber: String,
    country: String,
    message: String
): ContactUsResponse {
    val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
    }
    val endPoint = "contact"
    val requestUrl = "https://nserver.najih1.com/$endPoint"

    val requestBody = ContactUsRequest(name, email, phoneNumber, country, message)
    val requestBodyString = json.encodeToString(requestBody)

    Log.d("ApiClient", "Making post request to URL: $requestUrl")
    Log.d("ApiClient", "Request Body: $requestBodyString")

    return try {
        val response: HttpResponse = httpClient.post(requestUrl) {
            contentType(ContentType.Application.Json)
            setBody(requestBody)
        }

        when (response.status) {
            HttpStatusCode.OK -> {
                val responseBody = response.bodyAsText()
                Log.d("ApiClient", "Response Body: $responseBody")
                json.decodeFromString(responseBody)
            }
            else -> {
                val errorMessage = "Sending Contact Form failed with status ${response.status}\nResponse: ${response.bodyAsText()}"
                Log.e("ApiClient", errorMessage)
                throw Exception(errorMessage)
            }
        }
    } catch (e: Exception) {
        Log.e("ApiClient", "Error during sending the contact form: ${e.message}", e)
        throw e
    }
}
