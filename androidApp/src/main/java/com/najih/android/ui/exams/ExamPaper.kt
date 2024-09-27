package com.najih.android.ui.exams

import android.content.Context
import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.animation.togetherWith
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import com.najih.android.api.exams.getExamById
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.decode.ImageSource
import com.najih.android.dataClasses.Description
import com.najih.android.dataClasses.Exam
import com.najih.android.dataClasses.Image
import com.najih.android.dataClasses.Name
import com.najih.android.dataClasses.Question
import com.najih.android.util.GlobalFunctions
import io.ktor.client.HttpClient
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ExamPaper(navController: NavController,
              httpClient: HttpClient,
              context: Context,
              examId: String){

    val userName = GlobalFunctions.getUserInfo(context).userName
    val userAnswers = remember { mutableStateMapOf<Int, Char>() }
    var exam by remember { mutableStateOf<Exam?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var remainingTime by remember { mutableIntStateOf(0) }
    var currentQuestionIndex by remember { mutableIntStateOf(0) }
    var showReviewDialog by remember {
        mutableStateOf<Boolean>(false)
    }
    val coroutineScope = rememberCoroutineScope()
    // Fetch exam details
    LaunchedEffect(Unit) {
        try {
            exam = getExamById(httpClient, context, examId)
            exam?.let {
                remainingTime = it.time
                coroutineScope.launch {
                    while (remainingTime > 0) {
                        delay(1000)
                        remainingTime -= 1
                    }

                    Log.d("ExamPaper", "Time is up!")
                    // You might want to navigate or submit results here
                }
            }
        } catch (e: Exception) {
            errorMessage = "Error fetching exam"
            Log.e("ExamPaperError", "Failed to fetch exam", e)
        } finally {
            isLoading = false
        }
    }
    Column( modifier = Modifier
        .background(Color(0xfff9f9f9))
        .fillMaxSize()
        .padding(6.dp)) {
        // Display loading, error, or exam details
        if (isLoading) {
            Text("Loading exam...")
        } else if (errorMessage != null) {
            Text(errorMessage ?: "Unknown error")
        } else {
            exam?.let { exam ->
                if (userName != null) {
                    ExamHeader(
                        studentName = userName,
                        examName = exam.name.en,
                        currentQuestion = currentQuestionIndex + 1,
                        totalQuestions = exam.questions.size,
                        remainingTime = "${remainingTime / 60}:${remainingTime % 60}"
                    )
                }
                AnimatedContent(targetState = currentQuestionIndex,
                    transitionSpec = {
                        (expandIn(animationSpec = tween(durationMillis = 300)) + fadeIn()).togetherWith(
                            shrinkOut(animationSpec = tween(durationMillis = 300)) + fadeOut()
                        )
                    }, label = ""
                ) { targetIndex ->
                        QuestionCard(
                            question = exam.questions[targetIndex],
                            index = targetIndex,
                            userAnswers,
                            onAnswer = { answer ->
                                Log.d("ExamPaper", "Answer selected: $answer")
                                userAnswers[targetIndex] = answer.selectedOption
                                Log.d("ExamPaper", "userAnswers: $userAnswers" +
                                        "")
                            }
                        )

                }

                if (showReviewDialog) {
                    ReviewDialog(
                        totalQuestions = exam.questions.size,
                        userAnswers = userAnswers,
                        onQuestionClick = { selectedQuestionIndex ->
                            currentQuestionIndex = selectedQuestionIndex
                            showReviewDialog = false
                        },
                        onDismiss = { showReviewDialog = false }
                    )
                }
                ExamBottomNavBar(onReviewClick =  { showReviewDialog = true },
                    onSubmitClick = { /*TODO*/ },
                    onNextClick = {currentQuestionIndex = (currentQuestionIndex + 1) % exam.questions.size})

            }

        }
    }
}


