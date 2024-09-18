package com.najih.android.ui.exams

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ReviewDialog(
    totalQuestions: Int,
    userAnswers: Map<Int, Char>,
    onQuestionClick: (Int) -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            Button(onClick = { onDismiss() }) {
                Text("Close")
            }
        },
        title = { Text("Review Questions") },
        text = {
            Column (modifier = Modifier.fillMaxWidth()){

                // Create a grid of question numbers
                val rows = (0 until totalQuestions).chunked(5)
                rows.forEach { row ->
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
                        row.forEach { questionIndex ->
                            val backgroundColor = if (userAnswers.containsKey(questionIndex)) {
                                Color.Green // Question answered
                            } else {
                                Color.Gray // Question skipped or unanswered
                            }
                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .padding(2.dp)
                                    .clip(CircleShape)
                                    .background(backgroundColor)
                                    .clickable { onQuestionClick(questionIndex) }
                                    .padding(8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "${questionIndex + 1}",
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewReviewDialog() {
    val userAnswers = mapOf(0 to 'A', 3 to 'B', 5 to 'C')
    MaterialTheme {
        ReviewDialog(
            totalQuestions = 7,
            userAnswers = userAnswers,
            onQuestionClick = { /* Handle question click */ },
            onDismiss = { /* Handle dismiss */ }
        )
    }
}
