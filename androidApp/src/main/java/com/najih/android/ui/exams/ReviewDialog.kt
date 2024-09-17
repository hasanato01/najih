package com.najih.android.ui.exams

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
            Column {
                (0 until totalQuestions).forEach { questionIndex ->
                    val backgroundColor = if (userAnswers.containsKey(questionIndex)) {
                        Color.Green // Question answered
                    } else {
                        Color.Gray // Question skipped or unanswered
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .background(backgroundColor)
                            .clickable { onQuestionClick(questionIndex) }
                            .padding(16.dp)
                    ) {
                        Text("Question ${questionIndex + 1}")
                    }
                }
            }
        }
    )
}
