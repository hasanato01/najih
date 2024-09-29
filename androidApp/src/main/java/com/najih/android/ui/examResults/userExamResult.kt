package com.najih.android.ui.examResults

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.najih.android.api.exams.getExamResultById
import com.najih.android.dataClasses.QuestionResultData
import com.najih.android.dataClasses.SubmitExamRequest
import io.ktor.client.HttpClient


@Composable
fun UserExamResult(httpClient: HttpClient, context: Context, examResultId: String) {
    var examResult by remember { mutableStateOf<SubmitExamRequest?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(true) }
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

    if (isLoading) {
        // Show loading UI
        CircularProgressIndicator(modifier = Modifier.fillMaxSize().wrapContentSize())
    } else if (examResult != null) {
        // Display the exam result details and questions
        val result = examResult!!

        Column(modifier = Modifier.padding(16.dp)) {
            // Header
            Text(
                text = result.examName.en,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Submitted At: ${result.submittedAt}",
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

            // Map over the questions and display the results
            result.results.forEachIndexed { index, questionResult ->
                QuestionResultCard(questionResult, index + 1)
            }
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

@Composable
fun QuestionResultCard(result: QuestionResultData, questionIndex: Int) {
    // Display if the question was correct or not
    val isCorrectText = if (result.isCorrect) "Correct" else "Incorrect"
    val isCorrectColor = if (result.isCorrect) Color.Green else Color.Red

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .shadow(4.dp, RoundedCornerShape(8.dp)),

    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Question index and correctness
            Text(
                text = "Question $questionIndex - $isCorrectText",
                color = isCorrectColor,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Display question image
            AsyncImage(
                model = result.question.image.url,
                contentDescription = "Question Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(8.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Display answer options
            Text(text = "Your Answer: ${result.userAnswer}", fontSize = 14.sp)
            Text(text = "Correct Answer: ${result.correctAnswer}", fontSize = 14.sp)
        }
    }
}