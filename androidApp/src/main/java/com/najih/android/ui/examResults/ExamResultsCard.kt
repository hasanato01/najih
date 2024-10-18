package com.najih.android.ui.examResults

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.najih.android.R
import com.najih.android.dataClasses.SubmitExamRequest
import com.najih.android.util.GlobalFunctions
import kotlinx.coroutines.launch
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter


@SuppressLint("SuspiciousIndentation")
@Composable
fun ExamResultsCard (navController: NavController, examResult: SubmitExamRequest) {
    val context = LocalContext.current
    val currentLanguage by remember { mutableStateOf(GlobalFunctions.getUserLanguage(context) ?: "en") }
    val coroutineScope = rememberCoroutineScope()
    val examResultId = examResult.id
    val examName = when(currentLanguage) {
        "en" ->  examResult.examName.en
        "ar" ->  examResult.examName.ar
        else ->  examResult.examName.en
    }

    val numberOfQuestions = examResult.totalQuestions
    val correctAnswers = examResult.correctAnswersCount
    val submittedAt = examResult.submittedAt
    val parsedDate = ZonedDateTime.parse(submittedAt)
    val formatter = DateTimeFormatter.ofPattern("d/M/yyyy HH:mm")
    val formattedDate = parsedDate.format(formatter)

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .padding(10.dp)
                .clickable {
                    coroutineScope.launch {
                        navController.navigate("exam_result/${examResultId}")
                    }
                },
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
            shape = RoundedCornerShape(5.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth().
                        fillMaxHeight()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Left section - Info
                Column {
                    Text(
                        text = examName,
                        fontSize = 20.sp,
                    )

                    Text(
                        text = stringResource(id = R.string.correct_answers, correctAnswers, numberOfQuestions),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(top = 11.dp)
                    )

                    Text(
                        text = stringResource(id = R.string.examResult_time_label, formattedDate),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(top = 11.dp)
                    )
                }

                // Right section - Circular percentage indicator
                val percentage = (correctAnswers.toFloat() / numberOfQuestions.toFloat()) * 100

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.size(100.dp) // Adjust the size of the circle
                ) {
                    CircularProgressIndicator(
                        progress = { correctAnswers.toFloat() / numberOfQuestions.toFloat() },
                        modifier = Modifier.size(100.dp), // Same size as the box
                        color = Color(0xFFfCAF50),
                        strokeWidth = 6.dp,
                    )
                    Text(
                        text = "${percentage.toInt()}%", // Show percentage inside circle
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
            }
        }

    }