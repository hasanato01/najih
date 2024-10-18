package com.najih.android.ui.recordedLessons

import Lesson
import android.util.Log
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.najih.android.R

@Composable
fun LessonCard( lesson: Lesson ,  lessonNumber: Int) {
    Log.d("ApiClient", "Making GET request to URL: $lesson")
    val lessonTitle = lesson.name.en.takeIf { it.isNotEmpty() } ?: "No Lesson Title Available"
    val lessonDesc = lesson.description.en.takeIf { it.isNotEmpty() } ?: "No description available."
    val startDate = lesson.startDate.takeIf { it.isNotEmpty() } ?: "Start Date: Unknown"
    val endDate = lesson.endDate.takeIf { it.isNotEmpty() } ?: "End Date: Unknown"
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(200.dp)
            .padding(top = 20.dp)
            .shadow(4.dp, RoundedCornerShape(5.dp))
    ) {
        Card(
            modifier = Modifier.fillMaxSize(),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
            shape = RoundedCornerShape(5.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier.padding(10.dp)
            ) {
                Text(
                    text = lessonTitle,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light
                )
                Text(
                    text = lessonDesc,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier.padding(top = 16.dp)
                )
                Text(
                    text = stringResource(R.string.startdata)+startDate,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(top = 11.dp)
                )
                Text(
                    text = "endDate : $endDate",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(top = 11.dp)
                )
            }
        }
    }
}