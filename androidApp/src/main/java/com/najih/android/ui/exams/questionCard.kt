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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.najih.android.R
import com.najih.android.dataClasses.Answer
import com.najih.android.dataClasses.Question
import com.najih.android.util.NetworkImage

@Composable
fun QuestionCard(question: Question, index: Int,userAnswers: MutableMap<Int, Char>, onAnswer: (Answer) -> Unit) {
    val screenHeight = LocalConfiguration.current.screenHeightDp
    val selectedAnswer = userAnswers[index]
    Column(modifier = Modifier.padding(16.dp)) {
        // Display question image
        Image(
            painter = rememberAsyncImagePainter(model = question.image.url),
            contentDescription = "Profile Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height((screenHeight / 2.2).dp)
                .clip(RoundedCornerShape(8.dp))

        )

        Column(modifier = Modifier.padding(top = 8.dp)) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "A.${question.A}",
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            onAnswer(Answer(index, 'A'))
                            userAnswers[index] = 'A'
                        }
                        .clip(RoundedCornerShape(8.dp))
                        .padding(8.dp)
                        .background(
                            if (selectedAnswer == 'A') Color.Blue else Color.Transparent
                        ).padding(start = 16.dp,end= 16.dp, top = 8.dp, bottom = 8.dp)
                )

                Text(
                    text = "B.${question.B}",
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            onAnswer(Answer(index, 'B'))
                            userAnswers[index] = 'B'
                        }
                        .clip(RoundedCornerShape(8.dp))
                        .padding(8.dp)
                        .background(
                            if (selectedAnswer == 'B') Color.Blue else Color.Transparent
                        ).padding(start = 16.dp,end= 16.dp, top = 8.dp, bottom = 8.dp)
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
                        .clip(RoundedCornerShape(8.dp))
                        .padding(8.dp)
                        .background(
                            if (selectedAnswer == 'C') Color.Blue else Color.Transparent
                        ).padding(start = 16.dp,end= 16.dp, top = 8.dp, bottom = 8.dp)
                )

                Text(
                    text = "D.${question.D}",
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            onAnswer(Answer(index, 'D'))
                            userAnswers[index] = 'D'
                        }
                        .clip(RoundedCornerShape(8.dp))
                        .padding(8.dp)
                        .background(
                            if (selectedAnswer == 'D') Color.Blue else Color.Transparent
                        ).padding(start = 16.dp,end= 16.dp, top = 8.dp, bottom = 8.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewQuestionCard() {
    val sampleQuestion = Question(
        A = true,
        B = false,
        C = true,
        D = false,
        image = com.najih.android.dataClasses.Image(
            filename = "sample_image.jpg",
            url = "https://example.com/images/sample_image.jpg",
            message = "Sample Question Image",
            status = 200
        )
    )
    val userAnswers = mutableMapOf<Int, Char>()
    MaterialTheme {
        QuestionCard(
            question = sampleQuestion,
            index = 0,
            userAnswers = userAnswers,
            onAnswer = { /* Handle answer */ }
        )
    }
}