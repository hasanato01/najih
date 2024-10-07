package com.najih.android.ui.examResults

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.najih.android.api.exams.getExamResultById
import com.najih.android.dataClasses.SubmitExamRequest
import com.najih.android.ui.uitilis.BottomNavBar

import com.najih.android.ui.uitilis.Navbar
import io.ktor.client.HttpClient
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter


@Composable
fun UserExamResult(navController: NavController,httpClient: HttpClient, context: Context, examResultId: String) {
    var examResult by remember { mutableStateOf<SubmitExamRequest?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    val submittedAt = examResult?.submittedAt
    val formattedDate = if (submittedAt != null) {
        try {
            val parsedDate = ZonedDateTime.parse(submittedAt)
            val formatter = DateTimeFormatter.ofPattern("d/M/yyyy HH:mm")
            parsedDate.format(formatter)
        } catch (e: Exception) {
            "Invalid Date"
        }
    } else {
        "No Submission Date"
    }

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
                backText = formattedDate,
                titleText = examResult?.examName?.en ?: "Exam Title"
            )
        },
        bottomBar = { BottomNavBar(navController) }
    ) { innerPadding ->
        Column(modifier = Modifier
            .padding(innerPadding)
            .padding(15.dp)) {

            if (isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (examResult != null) {
                val result = examResult!!

                Column(modifier = Modifier.padding(15.dp)) {
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

                if (!result.results.isNullOrEmpty()) {
                    LazyColumn {
                        itemsIndexed(result.results) { index, questionResult ->
                            QuestionResultCard(questionResult, questionIndex = index + 1)
                        }
                    }
                } else {
                    Text(
                        text = "No results available for this exam.",
                        fontSize = 16.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            } else if (errorMessage != null) {
                Text(
                    text = errorMessage ?: "Unknown error",
                    color = Color.Red,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}
