package com.najih.android.ui.exams

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.najih.android.R

@Composable
fun ExamHeader(
    studentName: String,
    examName: String,
    currentQuestion: Int,
    totalQuestions: Int,
    remainingTime: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF00004B))
            .padding(16.dp)
            .statusBarsPadding()
            .height(150.dp)
    ) {

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
                Text(
                    text = studentName,
                    color = Color.LightGray,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = examName,
                    color = Color.White,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "$currentQuestion/$totalQuestions",
                    color = Color.White,
                    fontSize = 30.sp,
                )
            }

        Column (
            modifier = Modifier
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,

        ){
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(120.dp)  // Adjust size as needed
                    .border(
                        BorderStroke(2.dp, Color.White),  // Set circular border to white
                        shape = CircleShape
                    )
            ) {
                Text(
                    text = remainingTime,  // e.g., "15:30"
                    fontSize = 40.sp,
                    color = Color.White,
                    fontWeight = FontWeight.W100// Set text color to white
                )
            }
        }

    }
}


@Preview(showBackground = true)
@Composable
fun PreviewExamHeader() {
    MaterialTheme {
        ExamHeader(
            studentName = "John Doe",
            examName = "Math Test",
            currentQuestion = 2,
            totalQuestions = 10,
            remainingTime = "15:30"
        )
    }
}