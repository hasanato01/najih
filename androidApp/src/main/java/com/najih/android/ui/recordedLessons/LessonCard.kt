package com.najih.android.ui.recordedLessons

import Lesson
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.najih.android.R
import com.najih.android.util.GlobalFunctions



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
    val context = LocalContext.current
    val currentLanguage by remember { mutableStateOf(GlobalFunctions.getUserLanguage(context) ?: "ar") }
    val lessonName = if (currentLanguage == "ar") lesson.name.ar else lesson.name.en
    val lessonDescription = if (currentLanguage == "ar") lesson.description.ar else lesson.description.en
    val isFree = lesson.isFree

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(16.dp)
            .border(width = 0.5.dp, color = Color(0xb8b8b8FF), shape = RoundedCornerShape(8.dp))
            .clickable(enabled = isCheckableMode) {
                when {
                    isPurchased -> onLessonPurchasedClick()
                    isFree -> {} // Do nothing if the lesson is free
                    isCheckableMode -> onCheckedChange(!isChecked)
                }
            },
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
                        text = lessonName,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Light
                    )
                    Text(
                        text = lessonDescription,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Light,
                        color = Color(0xFF212529),
                        modifier = Modifier.padding(top = 20.dp)
                    )
                    Text(
                        text = stringResource(id = R.string.start_date, lesson.startDate),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                    Text(
                        text = stringResource(id = R.string.end_date, lesson.endDate),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                // Right Column for Checkboxes and Buttons
                Column(
                    horizontalAlignment = Alignment.End // Align buttons and checkbox to the right
                ) {
                    Box(
                        modifier = Modifier.fillMaxWidth(), // Make the Box fill the full width
                        contentAlignment = Alignment.CenterEnd // Align Checkbox to the end
                    ) {
                        if (isCheckableMode && !isPurchased && !isFree) {
                            Checkbox(
                                checked = isChecked,
                                onCheckedChange = onCheckedChange,
                                modifier = Modifier.padding(start = 8.dp),
                            )
                        }
                    }

                    Button(
                        onClick = { onPreviewLessonClick(lesson.link) },
                        modifier = Modifier
                            .padding(4.dp)
                            .padding(top = 50.dp)
                            .width(120.dp)
                            .height(40.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3))
                    ) {
                        Text(stringResource(R.string.preview_lesson), color = Color.White, fontSize = 12.sp)
                    }

                    if (isPurchased || isFree) {
                        Button(
                            onClick = { onPreviewLessonClick(lesson.exLink) },
                            modifier = Modifier
                                .padding(4.dp)
                                .width(120.dp)
                                .height(40.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3))
                        ) {
                            Text(stringResource(R.string.watch_lesson), color = Color.White, fontSize = 12.sp)
                        }
                    }
                }
            }

            if (isPurchased) {
                Tag(text = stringResource(R.string.purchased), color = Color(0xFF50c878), modifier = Modifier.align(Alignment.TopEnd))
            }
            if (isFree) {
                Tag(text = stringResource(R.string.free), color = Color(0xFFFFA500), modifier = Modifier.align(Alignment.TopEnd))
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


