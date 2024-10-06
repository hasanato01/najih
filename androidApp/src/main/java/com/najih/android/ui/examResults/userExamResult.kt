package com.najih.android.ui.examResults

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.najih.android.api.exams.getExamResultById
import com.najih.android.dataClasses.SubmitExamRequest
import com.najih.android.ui.uitilis.BottomNavBar
import com.najih.android.ui.uitilis.HomeNavbar
import com.najih.android.ui.uitilis.Navbar
import io.ktor.client.HttpClient
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter


@Composable
fun UserExamResult(httpClient: HttpClient, context: Context, examResultId: String) {
    val navController = rememberNavController()
    var examResult by remember { mutableStateOf<SubmitExamRequest?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    val submittedAt = examResult?.submittedAt ?: "ssss" // Use ?: to provide a default value
    val parsedDate = try {
        ZonedDateTime.parse(submittedAt)
    } catch (e: Exception) {
        null // Handle parse failure (e.g., if the string is not a valid date format)
    }
    val formatter = DateTimeFormatter.ofPattern("d/M/yyyy HH:mm")
    val formattedDate = parsedDate?.format(formatter) ?: "Invalid Date" // Provide a fallback if parsing fails

    Log.e("ExamPaperError", examResultId)

    LaunchedEffect(Unit) {
        try {
            examResult = getExamResultById(httpClient, context, examResultId)
        } catch (e: Exception) {
            errorMessage = "Error fetching exam result"
            Log.e("ExamPaperError", "Failed to fetch exam result", e)
        } finally {
            isLoading = false
        }
    }


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Navbar(
                navController = navController,
                backText = formattedDate ,
                titleText = examResult?.examName?.en ?: "Exam Title" // Fallback if null
            )
        },
        bottomBar = { BottomNavBar(navController) }
    ) { innerPadding ->
        Column(modifier = Modifier
            .padding(innerPadding)
            .padding(15.dp)) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize())
            } else if (examResult != null) {
                // Display the exam result details
                val result = examResult!!
                val submittedAt = result.submittedAt
                val parsedDate = ZonedDateTime.parse(submittedAt)
                val formatter = DateTimeFormatter.ofPattern("d/M/yyyy HH:mm")
                val formattedDate = parsedDate.format(formatter)
                Column(modifier = Modifier.padding(15.dp)) {

                // Header
                Text(
                    text = result.examName.en,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "Submitted At: $formattedDate",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "Score: ${result.correctAnswersCount} / ${result.totalQuestions}",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "Correct: ${result.correctAnswersCount}, Incorrect: ${result.totalQuestions - result.correctAnswersCount}",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                    }

                // Check if results is not null or empty
                val resultsCount = result.results.size
                if (resultsCount > 0) {
                    // Display questions in a LazyColumn
                    LazyColumn {
                        itemsIndexed(result.results) { index, questionResult ->
                            QuestionResultCard(questionResult, questionIndex = index + 1) // Ensure index is an integer
                        }
                    }
                } else {
                    // Handle the case where there are no results
                    Text(
                        text = "No results available for this exam.",
                        fontSize = 16.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            } else if (errorMessage != null) {
                // Show error message
                Text(
                    text = errorMessage ?: "Unknown error",
                    color = Color.Red,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

