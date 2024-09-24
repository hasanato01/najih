import android.content.Context
import android.util.Log
import com.najih.android.api.globalData.BASE_URL
import com.najih.android.api.globalData.EXAM_ENDPOINT
import com.najih.android.dataClasses.Exam
import com.najih.android.util.GlobalFunctions
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.http.HttpHeaders
import kotlinx.serialization.json.Json

suspend fun GetAllExams(
    httpClient: HttpClient,
    context: Context // Add context to retrieve token
): List<Exam> {
    val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
    }

    val requestUrl = "${BASE_URL}${EXAM_ENDPOINT}"

    // Retrieve the JWT token using the getUserInfo function
    val token = GlobalFunctions.getUserInfo(context).token
    val user = GlobalFunctions.getUserInfo(context).userName

    // Log the request details
    Log.d("ApiClient", "Making GET request to URL: $requestUrl")
    Log.d("ApiClient", "token: $token for $user")
    Log.d("ApiClient", "Content-Type: ${ContentType.Application.Json}")

    return try {
        val response: HttpResponse = httpClient.get(requestUrl) {
            contentType(ContentType.Application.Json)
            // Add the token to the Authorization header if it's not null
            token?.let {
                headers {
                    append(HttpHeaders.Authorization, "Bearer $token")
                }
            }
        }

        when (response.status) {
            HttpStatusCode.OK -> {
                val responseBody = response.bodyAsText()
                Log.d("ApiClient", "Response Body: $responseBody")
                json.decodeFromString(responseBody)  // Deserialize into a list of Exam objects
            }
            else -> {
                val errorMessage = "Get Exams failed with status ${response.status}\nResponse: ${response.bodyAsText()}"
                Log.e("ApiClient", errorMessage)
                throw Exception(errorMessage)
            }
        }
    } catch (e: Exception) {
        Log.e("ApiClient", "Error during getting exams: ${e.message}", e)
        throw e
    }
}
