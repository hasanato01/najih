package com.najih.android.ui.examResults

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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.najih.android.dataClasses.SubmitExamRequest
import kotlinx.coroutines.launch

@Composable
fun ExamResultsCard (navController: NavController, examResult: SubmitExamRequest) {

    val coroutineScope = rememberCoroutineScope()
    val examResultId = examResult.id
    val examName = examResult.examName.en
    val numberOfQuestions = examResult.totalQuestions
    val submittedAt = examResult.submittedAt
    Box(
    modifier = Modifier
        .fillMaxWidth()
        .height(200.dp)
        .padding(top = 20.dp)
        .shadow(4.dp, RoundedCornerShape(5.dp))
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    coroutineScope.launch {
                        navController.navigate("exam_result/${examResultId}")
                    }
                },
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
            shape = RoundedCornerShape(5.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier.padding(10.dp)
            ) {
                if (examResultId != null) {
                    Text(
                        text = examResultId,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Light
                    )
                }
                Text(
                    text = examName,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light
                )

                Text(
                    text = "Questions : $numberOfQuestions",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(top = 11.dp)
                )
                Text(
                    text = "Time : $submittedAt",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(top = 11.dp)
                )
            }
        }
    }
}