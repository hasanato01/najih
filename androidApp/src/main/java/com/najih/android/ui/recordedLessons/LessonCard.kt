package com.najih.android.ui.recordedLessons

import Lesson
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.najih.android.R
import com.najih.android.util.VideoPlayer
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment


@Composable
fun LessonCard(
    lesson: Lesson,
    lessonNumber: Int,
    isCheckableMode: Boolean,
    isChecked: Boolean,
    isPurchased: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    onLessonPurchasedClick: () -> Unit,
    onPreviewLessonClick: (String) -> Unit
) {

    val isFree = lesson.isFree

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(16.dp)
            .clickable(enabled = isCheckableMode) {
                when {
                    isPurchased -> onLessonPurchasedClick()
                    isFree -> {} // Do nothing if the lesson is free
                    isCheckableMode -> onCheckedChange(!isChecked)
                }
            },
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF9F9F9))
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Lesson Details
            Row(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = lesson.name.en,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Light
                    )
                    Text(
                        text = lesson.description.en,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Light,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                    Text(
                        text = "Start Date: ${lesson.startDate}",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                    Text(
                        text = "End Date: ${lesson.endDate}",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                // Right Column for Checkboxes and Buttons
                Column {
                    if (isCheckableMode && !isPurchased && !isFree) {
                        Checkbox(
                            checked = isChecked,
                            onCheckedChange = onCheckedChange,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                    }
                    Button(
                        onClick = { onPreviewLessonClick(lesson.link) },
                        modifier = Modifier.padding(4.dp).padding(top = 50.dp).width(120.dp).height(40.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3))
                    ) {
                        Text("Preview Lesson", color = Color.White, fontSize = 12.sp)
                    }
                    if (isPurchased || isFree) {
                        Button(
                            onClick = { /* Handle action for purchased or free lesson */ },
                            modifier = Modifier.padding(4.dp).width(120.dp).height(40.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
                        ) {
                            Text("Watch Lesson", color = Color.White, fontSize = 12.sp)
                        }
                    }
                }
            }

            if (isPurchased) {
                Tag(text = "Purchased", color = Color(0xFF50c878), modifier = Modifier.align(Alignment.TopEnd))
            }
            if (isFree) {
                Tag(text = "Free", color = Color(0xFFFFA500), modifier = Modifier.align(Alignment.TopEnd))
            }

        }
    }
}

@Composable
fun Tag(text: String, color: Color, modifier: Modifier = Modifier) {
    Text(
        text = text,
        color = Color.White,
        fontWeight = FontWeight.Bold,
        modifier = modifier
            .padding(8.dp)
            .background(color = color, shape = RoundedCornerShape(4.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    )
}


