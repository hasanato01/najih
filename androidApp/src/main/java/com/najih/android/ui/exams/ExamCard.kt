package com.najih.android.ui.exams

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.najih.android.dataClasses.Exam
import com.najih.android.util.GlobalFunctions

@Composable
fun ExamCard( navController: NavController,exam: Exam) {
    val context= LocalContext.current
    val currentLanguage by remember { mutableStateOf(GlobalFunctions.getUserLanguage(context) ?: "en") }
    val examId = exam.id
    val examName = when (currentLanguage) {
        "ar" -> exam.name.ar
        else -> exam.name.en
    }

    val examDesc = when (currentLanguage) {
        "ar" -> exam.describtion.ar
        else -> exam.describtion.en
    }
    val numberOfQuestions = exam.questions.size
    val examTime = exam.time
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(10.dp)
            .border(width = 0.5.dp,  color = Color(0xb8b8b8FF), shape = RoundedCornerShape(8.dp))
            .clickable {
                navController.navigate("exam_paper/$examId")
            }
    ) {

            Column(
                modifier = Modifier.padding(10.dp)
            ) {
                Text(
                    text = examName,
                    fontSize = 20.sp,
                )
                Text(
                    text = examDesc,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier.padding(top = 16.dp)
                )
                Text(
                    text = stringResource(id = R.string.questions_label, numberOfQuestions),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(top = 11.dp)
                )
                Text(
                    text = stringResource(id = R.string.time_label, examTime),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(top = 11.dp)
                )
            }
        }
    }
