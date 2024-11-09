package com.najih.android.api.purchasedLessons


import Lesson
import android.content.Context
import com.najih.android.api.globalData.SEND_PURCHASE_REQUEST_ENDPOINT
import android.util.Log
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.snapshots.SnapshotStateMap
import com.najih.android.api.globalData.BASE_URL
import com.najih.android.api.globalData.UPLOAD_FILE_ENDPOINT
import com.najih.android.dataClasses.Bill
import com.najih.android.dataClasses.PurchaseRequest
import com.najih.android.dataClasses.PurchaseResponse
import com.najih.android.util.GlobalFunctions
import io.ktor.client.HttpClient
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

suspend fun savePurchasedLessons(
    httpClient: HttpClient,
    context: Context,
    purchasedLessons: SnapshotStateMap<String, MutableList<String>>,
    recorderLessonsIds: SnapshotStateList<String>,
    recorderLessons: SnapshotStateList<Lesson>,
    file: File
): PurchaseResponse {
    val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
    }

    Log.d("ApiClient", "Purchase Request JSON: $file")
    val uploadUrl = "${BASE_URL}${UPLOAD_FILE_ENDPOINT}"
    val purchaseUrl = "${BASE_URL}${SEND_PURCHASE_REQUEST_ENDPOINT}"
    val token = GlobalFunctions.getUserInfo(context).token
    val userName = GlobalFunctions.getUserInfo(context).userName
    val userEmail = GlobalFunctions.getUserInfo(context).userEmail


    try {
        // First Request: Upload the file
        val fileResponse: HttpResponse = httpClient.post(uploadUrl) {
            headers {
                append(HttpHeaders.Authorization, "Bearer $token")
            }
            setBody(MultiPartFormDataContent(formData {
                append("file", file.readBytes(), Headers.build {
                    append(HttpHeaders.ContentDisposition, "filename=\"${file.name}\"")
                })
            }))
        }

        if (fileResponse.status != HttpStatusCode.OK) {
            throw Exception("File upload failed with status ${fileResponse.status}")
        }

        val fileResponseBody = fileResponse.bodyAsText()
        var bill = json.decodeFromString<Bill>(fileResponseBody)
        bill = bill.copy(status = 200)
        // Convert state lists to standard lists
        val standardPurchasedLessons = purchasedLessons.toMap().mapValues { it.value.toList() }
        val standardRecorderLessonsIds = recorderLessonsIds.toList()
        // Create the updated purchase request
        val updatedPurchaseRequest = PurchaseRequest(
            purchasedLessons = standardPurchasedLessons,
            recorderLessonsIds =standardRecorderLessonsIds,
            bill = bill, // Ensure bill has valid data
            userName = userName,
            userEmail = userEmail,
            recorderLessons = recorderLessons.toList(),
            status = "in progress",
            price = 0
        )

        // Log the purchase request JSON
        val purchaseRequestJson = json.encodeToString(updatedPurchaseRequest)
        Log.d("ApiClient", "Purchase Request JSON: $purchaseRequestJson")

        // Second Request: Send the updated purchase request
        val purchaseResponse: HttpResponse = httpClient.post(purchaseUrl) {
            headers {
                append(HttpHeaders.Authorization, "Bearer $token")
                contentType(ContentType.Application.Json) // Set the content type for JSON
            }
            setBody(updatedPurchaseRequest) // Send the serialized request
        }

        return when (purchaseResponse.status) {
            HttpStatusCode.OK -> {
                val responseBody = purchaseResponse.bodyAsText()
                Log.d("ApiClient", "Response Body: $responseBody")
                json.decodeFromString<PurchaseResponse>(responseBody)
            }
            else -> {
                val errorMessage =
                    "Save purchased lessons failed with status ${purchaseResponse.status}\nResponse: ${purchaseResponse.bodyAsText()}"
                Log.e("ApiClient", errorMessage) // Log error details
                throw Exception(errorMessage)
            }
        }
    } catch (e: Exception) {
        Log.e("ApiClient", "Error during savePurchasedLessons: ${e.message}", e)
        throw e
    }
}