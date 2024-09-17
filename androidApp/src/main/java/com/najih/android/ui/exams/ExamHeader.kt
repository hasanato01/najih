package com.najih.android.ui.exams

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
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
    ) {
        // Left side: Student details and exam info
        Column(
            modifier = Modifier.weight(1f)  // Take up 1 portion of available space
        ) {
            // Student name with icon
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.user),  // Assuming you have a student icon
                    contentDescription = "Student Icon",
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = studentName,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            // Exam name with icon
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.live_icon),  // Assuming you have an exam icon
                    contentDescription = "Exam Icon",
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = examName,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            // Current question progress with icon
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.play),  // Progress icon
                    contentDescription = "Progress Icon",
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
                    BorderStroke(2.dp, Color.Gray),  // Circular border
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
