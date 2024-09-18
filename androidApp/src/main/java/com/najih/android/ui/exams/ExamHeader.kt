package com.najih.android.ui.exams

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
            .padding(16.dp)
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(16.dp),
                clip = false
            )
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(16.dp)
            .height(120.dp)
    ) {

        Column(
            modifier = Modifier.weight(1f)
        ) {
            // Student name with icon
            Row(verticalAlignment = Alignment.CenterVertically,modifier = Modifier.weight(1f) ) {
                Icon(
                    painter = painterResource(id = R.drawable.user),
                    contentDescription = "Student Icon",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = studentName,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            // Exam name with icon
            Row(verticalAlignment = Alignment.CenterVertically,modifier = Modifier.weight(1f) ) {
                Icon(
                    painter = painterResource(id = R.drawable.test),
                    contentDescription = "Exam Icon",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = examName,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            // Current question progress with icon
            Row(verticalAlignment = Alignment.CenterVertically,modifier = Modifier.weight(1f) ) {
                Icon(
                    painter = painterResource(id = R.drawable.questions),  // Progress icon
                    contentDescription = "Progress Icon",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = "$currentQuestion/$totalQuestions",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }

        // Right side: Timer with circular border
        Box(contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(100.dp)  // Adjust size as needed
                .border(
                    BorderStroke(2.dp, Color.Blue),  // Circular border
                    shape = CircleShape
                )
        ) {
            Text(
                text = remainingTime,  // e.g., "15:30"
                style = MaterialTheme.typography.headlineMedium
            )
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