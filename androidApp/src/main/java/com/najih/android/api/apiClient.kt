//// ApiClient.kt
//package com.najih.android.network
//
//import android.util.Log
//import com.najih.android.Data.SignInRequest
//import com.najih.android.Data.SignInResponse
//import com.najih.android.Data.SignUpRequest
//import com.najih.android.Data.SignUpResponse
//import io.ktor.client.*
//import io.ktor.client.call.body
//import io.ktor.client.engine.android.*
//import io.ktor.client.plugins.contentnegotiation.*
//import io.ktor.client.plugins.logging.*
//import io.ktor.client.request.*
//import io.ktor.client.statement.HttpResponse
//import io.ktor.client.statement.bodyAsText
//import io.ktor.http.*
////import io.ktor.serialization.kotlinx.json.*
//import kotlinx.serialization.encodeToString
//import kotlinx.serialization.json.Json
//
//object ApiClient {
//
//    private val client = HttpClient(Android) {
//        install(ContentNegotiation) {
////            json(Json {
////                prettyPrint = true
////                isLenient = true
////                ignoreUnknownKeys = true
////            })
//        }
//        install(Logging) {
//            logger = Logger.DEFAULT
//            level = LogLevel.HEADERS
//            filter { request ->
//                request.url.host.contains("ktor.io")
//            }
//
//        }
//    }
//
//    suspend fun signIn(email: String, password: String): SignInResponse {
//        val request = SignInRequest(email, password)
//
//        // Serialize the request to JSON
//        val jsonBody = Json.encodeToString(request)
//
//        return try {
//            val response: HttpResponse = client.post("https://nserver.najih1.com/users/login") {
//                contentType(ContentType.Application.Json)
//                setBody(jsonBody)  // Pass the serialized JSON string
//            }
//
//            val responseBody = response.bodyAsText()
//            Log.d("ApiClient", "Response Body: $responseBody")
//
//            // Deserialize the response to SignInResponse
//            Json.decodeFromString(responseBody)
//        } catch (e: Exception) {
//            Log.e("ApiClient", "Error during signIn: ${e.message}", e)
//            throw e  // Re-throw the exception after logging it
//        }
//    }
//    suspend fun signUp(name: String, phone: String, email: String, password: String): SignUpResponse {
//        val response: HttpResponse = client.post("https://nserver.najih1.com/") {
//            contentType(ContentType.Application.Json)
//            setBody(SignUpRequest(name, phone, email, password))
//        }
//        val responseBody = response.bodyAsText()
//        Log.d("ApiClient", "Response Body: $responseBody")
//        return response.body()
//    }
//}
