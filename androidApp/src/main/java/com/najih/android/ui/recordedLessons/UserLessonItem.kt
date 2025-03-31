package com.najih.android.ui.recordedLessons

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import com.najih.android.R
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.najih.android.dataClasses.MyRecorderLessonsResponse
import com.najih.android.util.YouTubePlayerDialog

@Composable
fun UserLessonItem(lesson: MyRecorderLessonsResponse, navController: NavController) {
    var previewVideoId by remember { mutableStateOf<String?>(null) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.White, Color(0xFFEFEFEF))
                    )
                )
                .padding(16.dp)
        ) {
            // Lesson Title
            Text(
                text = lesson.name.ar,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF333333)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Lesson Description
            Text(
                text = lesson.description.ar,
                fontSize = 14.sp,
                color = Color.Gray,
                lineHeight = 18.sp
            )

            Divider(color = Color.LightGray, thickness = 1.dp)

            Spacer(modifier = Modifier.height(10.dp))

            // Date & Time Row
            Column(
                modifier = Modifier.fillMaxWidth(0.5f),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                DateItem(iconRes = R.drawable.calendar, label = "Start", date = lesson.startDate , Modifier.fillMaxWidth())
                DateItem(iconRes = R.drawable.calendar, label = "End", date = lesson.endDate , Modifier.fillMaxWidth())
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = { previewVideoId = lesson.exLink }, // Set preview video ID
                    modifier = Modifier
                        .weight(1f)
                        .height(42.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9E9EEC)) // Light Purple
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.play),
                        contentDescription = "Watch",
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = stringResource(id = R.string.watch_now), fontSize = 14.sp, color = Color.White)
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = { navController.navigate("subject_lessons/${lesson.subjectId}") },
                    modifier = Modifier
                        .weight(1f)
                        .height(42.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE3E3E3)) // Light Gray
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.location_blue),
                        contentDescription = "Subject",
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = stringResource(id = R.string.go_to_subject), fontSize = 14.sp, color = Color.Black)
                }
            }
        }
    }

    // **Fix: Dialog should be inside the composable scope**
    if (previewVideoId != null) {
        YouTubePlayerDialog(
            videoId = previewVideoId!!,
            onDismiss = { previewVideoId = null }
        )
    }
}

// Custom DateItem for Better Visibility
@Composable
fun DateItem(iconRes: Int, label: String, date: String, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .height(35.dp)
            .background(Color(0xFFEAEAEA), shape = RoundedCornerShape(10.dp))
            .padding(horizontal = 10.dp, vertical = 6.dp)
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = label,
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = "$label: $date",
            fontSize = 12.sp,
            color = Color(0xFF555555)
        )
    }
}