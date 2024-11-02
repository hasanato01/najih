package com.najih.android.ui.exams

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.najih.android.dataClasses.Answer
import com.najih.android.dataClasses.Question
import kotlin.math.log

@Composable
fun QuestionCard(question: Question, index: Int,userAnswers: MutableMap<Int, Char>, onAnswer: (Answer) -> Unit) {
    val secureImageUrl = question.image.url.replace("http://", "https://")
    val screenHeight = LocalConfiguration.current.screenHeightDp
    val selectedAnswer = userAnswers[index]
    Log.d("question", question.toString())
    Column(modifier = Modifier.padding(16.dp)) {
        // Display question image
        Image(
            painter = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current)
                    .data(secureImageUrl)
                    .crossfade(true)
                    .build()
            ),
            contentDescription = "Profile Image",
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .height((screenHeight / 3).dp)
                .clip(RoundedCornerShape(8.dp))
        )


        Column(modifier = Modifier.padding(top = 8.dp)) {
            val answerOptions = listOf('A', 'B', 'C', 'D')
            val answers = listOf(question.A, question.B, question.C, question.D)

            answerOptions.forEachIndexed { index, option ->
                Text(
                    text = "$option",
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onAnswer(Answer(index, option))
                            userAnswers[index] = option
                        }
                        .clip(RoundedCornerShape(8.dp))
                        .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp)) // Add light border
                        .background(
                            if (selectedAnswer == option) Color(0xFFE0F7FA) else Color.Transparent // Very light background color
                        )
                        .padding(8.dp)
                        .padding( 8.dp)


                )
                if (index < answerOptions.size - 1) {
                    Spacer(modifier = Modifier.height(8.dp)) // Adjust the height as needed
                }
            }
        }

    }
}

