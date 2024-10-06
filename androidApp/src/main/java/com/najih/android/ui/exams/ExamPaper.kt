package com.najih.android.ui.exams

import LanguageContent
import android.content.Context
import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.najih.android.api.exams.submitExam
import com.najih.android.dataClasses.Exam
import com.najih.android.dataClasses.Question
import com.najih.android.dataClasses.QuestionResult
import com.najih.android.dataClasses.QuestionResultData
import com.najih.android.dataClasses.ResultImage
import com.najih.android.util.GlobalFunctions
import io.ktor.client.HttpClient
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun ExamPaper(
    navController: NavController,
    httpClient: HttpClient,
    context: Context,
    examId: String
) {
    val token = GlobalFunctions.getUserInfo(context).token
    val userID = GlobalFunctions.getUserInfo(context).userId
    val userName = GlobalFunctions.getUserInfo(context).userName
    val userAnswers = remember { mutableStateMapOf<Int, Char>() }
    var exam by remember { mutableStateOf<Exam?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var remainingTime by remember { mutableIntStateOf(0) }
    var currentQuestionIndex by remember { mutableIntStateOf(0) }
    var showReviewDialog by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    var showSubmissionDialog by remember { mutableStateOf(false) }
    var submissionMessage by remember { mutableStateOf("") }

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

                    // Trigger exam submission when time is up
                    val resultsArray = buildExamResults(exam!!, userAnswers)
                    val correctAnswersCount = calculateCorrectAnswersCount(exam!!, userAnswers)
                    val examName = LanguageContent(
                        en = exam!!.name.en,
                        ar = exam!!.name.ar
                    )
                    val submittedAt = System.currentTimeMillis().toString()

                    try {
                        val response = submitExam(
                            httpClient = httpClient,
                            userId = userID,
                            token = token,
                            examId = exam!!.id,
                            resultsArray = resultsArray,
                            correctAnswersCount = correctAnswersCount,
                            examName = examName,
                            totalQuestions = exam!!.questions.size,
                            submittedAt = submittedAt
                        )
                        submissionMessage = "Time is up! The exam has been submitted successfully."
                        showSubmissionDialog = true  // Show dialog when time is up
                        Log.d("ExamSubmission", "Exam submitted successfully: $response")
                    } catch (e: Exception) {
                        Log.e("ExamSubmission", "Error submitting exam: ${e.message}", e)
                    }
                }
            }
        } catch (e: Exception) {
            errorMessage = "Error fetching exam"
            Log.e("ExamPaperError", "Failed to fetch exam", e)
        } finally {
            isLoading = false
        }
    }


    Column(
        modifier = Modifier
            .background(Color(0xfff9f9f9))
            .fillMaxSize()
            .padding(6.dp)
    ) {
        // Display loading, error, or exam details
        if (isLoading) {
            Text("Loading exam...")
        } else if (errorMessage != null) {
            Text(errorMessage ?: "Unknown error")
        } else {
            exam?.let { exam ->

                    ExamHeader(
                        studentName = userName,
                        examName = exam.name.en,
                        currentQuestion = currentQuestionIndex + 1,
                        totalQuestions = exam.questions.size,
                        remainingTime = "${remainingTime / 60}:${remainingTime % 60}"
                    )

                AnimatedContent(
                    targetState = currentQuestionIndex,
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
                            Log.d("ExamPaper", "userAnswers: $userAnswers")
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
                if (showSubmissionDialog) {
                    AlertDialog(
                        onDismissRequest = { showSubmissionDialog = false },
                        confirmButton = {
                            TextButton(onClick = { showSubmissionDialog = false }) {
                                Text("OK")
                            }
                        },
                        title = {
                            Text(text = "Exam Submitted")
                        },
                        text = {
                            Text(text = submissionMessage)
                        }
                    )
                }

                ExamBottomNavBar(
                    onReviewClick = { showReviewDialog = true },
                    onSubmitClick = {
                        coroutineScope.launch {
                            val resultsArray = buildExamResults(exam, userAnswers)
                            val correctAnswersCount = calculateCorrectAnswersCount(exam, userAnswers)
                            val examName = LanguageContent(
                                en = exam.name.en,
                                ar = exam.name.ar
                            )
                            val submittedAt = System.currentTimeMillis().toString()
                            try {
                                val response = submitExam(
                                    httpClient = httpClient,
                                    userId = userID,
                                    token=token,
                                    examId = exam.id,
                                    resultsArray = resultsArray,
                                    correctAnswersCount = correctAnswersCount,
                                    examName = examName,
                                    totalQuestions = exam.questions.size,
                                    submittedAt = submittedAt
                                )
                                submissionMessage = "The exam has been submitted successfully."
                                showSubmissionDialog = true  // Show dialog for manual submission
                                Log.d("ExamSubmission", "Exam submitted successfully: $response")
                            } catch (e: Exception) {
                                Log.e("ExamSubmission", "Error submitting exam: ${e.message}", e)

                            }
                        }
                    },
                    onNextClick = {
                        currentQuestionIndex = (currentQuestionIndex + 1) % exam.questions.size
                    }
                )
            }
        }
    }
}



// Helper function to build the exam results
fun buildExamResults(exam: Exam, userAnswers: MutableMap<Int, Char>): List<QuestionResultData> {
    return exam.questions.mapIndexed { index, question ->
        val selectedAnswer = userAnswers[index] ?: ' '
        buildQuestionResult(question, selectedAnswer)
    }
}

fun calculateCorrectAnswersCount(exam: Exam, userAnswers: MutableMap<Int, Char>): Int {
    return exam.questions.mapIndexed { index, question ->
        val correctOption = when {
            question.A -> 'A'
            question.B -> 'B'
            question.C -> 'C'
            question.D -> 'D'
            else -> null
        }
        userAnswers[index] == correctOption
    }.count { it } // Count how many correct answers
}

// Helper function to convert a Question to a QuestionResult
fun mapQuestionToQuestionResult(question: Question): QuestionResult {
    return QuestionResult(
        A = question.A,
        B = question.B,
        C = question.C,
        D = question.D,
        image = ResultImage(
            filename = question.image.filename,
            url = question.image.url
        )
    )
}

fun buildQuestionResult(question: Question, selectedAnswer: Char): QuestionResultData {
    // Map the Question to QuestionResult
    val questionResult = mapQuestionToQuestionResult(question)

    // Determine the correct answer based on the boolean properties
    val correctAnswer = when {
        questionResult.A -> 'A'
        questionResult.B -> 'B'
        questionResult.C -> 'C'
        questionResult.D -> 'D'
        else -> ' ' // Fallback in case none are true, should not happen in a valid question
    }

    return QuestionResultData(
        question = questionResult,
        userAnswer = selectedAnswer.toString(),
        correctAnswer = correctAnswer.toString(),
        isCorrect = selectedAnswer == correctAnswer
    )
}


