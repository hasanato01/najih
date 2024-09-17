package com.najih.android.ui.exams

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.najih.android.R
import com.najih.android.dataClasses.Answer
import com.najih.android.dataClasses.Question
import com.najih.android.util.NetworkImage

@Composable
fun QuestionCard(question: Question, index: Int,userAnswers: MutableMap<Int, Char>, onAnswer: (Answer) -> Unit) {
    val screenHeight = LocalConfiguration.current.screenHeightDp
    val selectedAnswer = userAnswers[index]
    Column(modifier = Modifier.padding(8.dp)) {
        // Display question image
        Image(
            painter = painterResource(id = R.drawable.images),
            contentDescription = "Profile Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height((screenHeight / 2.2).dp)

        )

        Column(modifier = Modifier.padding(top = 8.dp)) {
            // First row with 2 answers (A and B)
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "A.${question.A}",
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            onAnswer(Answer(index, 'A'))
                            userAnswers[index] = 'A'
                        }
                        .padding(8.dp)
                        .background(
                            if (selectedAnswer =='A') Color.Green else Color.Transparent
                        )
                )

                Text(
                    text = "B.${question.B}",
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            onAnswer(Answer(index, 'B'))
                            userAnswers[index] = 'B'
                        }
                        .padding(8.dp)
                        .background(
                            if (selectedAnswer =='B') Color.Green else Color.Transparent
                        )
                )
            }

            // Second row with 2 answers (C and D)
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "C.${question.C}",
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            onAnswer(Answer(index, 'C'))
                            userAnswers[index] = 'C'
                        }
                        .padding(8.dp)
                        .background(
                            if (selectedAnswer =='C') Color.Green else Color.Transparent
                        )
                )

                Text(
                    text = "D.${question.D}",
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            onAnswer(Answer(index, 'D'))
                            userAnswers[index] = 'D'
                        }
                        .padding(8.dp)
                        .background(
                            if (selectedAnswer =='D') Color.Green else Color.Transparent
                        )
                )
            }
        }
    }
}
