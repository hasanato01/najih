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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.najih.android.dataClasses.Exam

@Composable
fun ExamCard( navController: NavController,exam: Exam) {
    val examId = exam.id
    val examName = exam.name.en
    val examDesc = exam.describtion.en
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
                    text = "Questions : $numberOfQuestions",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(top = 11.dp)
                )
                Text(
                    text = "Time : $examTime",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(top = 11.dp)
                )
            }
        }
    }