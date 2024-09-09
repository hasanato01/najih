package com.najih.android.ui.recordedLessons

import GetSubjectLessons
import GetSubjectsResponse
import LanguageContent
import Lesson
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.najih.android.ui.homePage.components.SearchBar
import com.najih.android.ui.navbar

@Composable
fun Lessons (navController: NavController ,subjectInfo : GetSubjectLessons ?) {
    val subjectName = subjectInfo?.name?.en ?: "unknown"
    val stage = subjectInfo?.level?.en + subjectInfo?.classNumber
    val lessonsList = subjectInfo?.listoflessons
    val showDialog = rememberSaveable { mutableStateOf(false) }
    Log.d("ApiClient", "Making GET request to URL: $subjectInfo")
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        navbar(navController)
        SearchBar()
        Text(
            text = subjectName,
            fontSize = 20.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 11.dp, top = 49.dp)
        )
        EnrollmentButtons(navController,stage , showDialog)
        lessonsList?.map { lesson ->
            LessonCard(lesson)
        }
        if(showDialog.value){
            EnrollmentDialog(onDismiss = { showDialog.value = false}, subjectInfo = subjectInfo )
        }

    }
}

@Composable
fun EnrollmentButtons(navController: NavController,stage : String , showDialog : MutableState<Boolean>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 28.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Button(
            onClick = { /* TODO */ },
            modifier = Modifier
                .weight(1f)
                .background(Color.White)
                .height(48.dp)
                .shadow(4.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            shape = RoundedCornerShape(5.dp)
        ) {
            Text(
                text = stage,
                fontSize = 14.sp,
                fontWeight = FontWeight.Light,
                color = Color.Black
            )
        }
        Button(
            onClick = { showDialog.value = true },
            modifier = Modifier
                .weight(1f)
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
            shape = RoundedCornerShape(5.dp)
        ) {
            Text(
                text = "Enroll Now",
                fontSize = 14.sp,
                fontWeight = FontWeight.Light,
                color = Color.White
            )
        }
    }
}
@Composable
fun LessonCard( lesson: Lesson) {
    Log.d("ApiClient", "Making GET request to URL: $lesson")
    val lessonTitle = lesson.name.en
    val lessonDesc = lesson.description.en
    val startData = lesson.startDate
    val endDate = lesson.endDate
    Box(
        modifier = Modifier
            .fillMaxWidth()
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
                    text = "startData : $startData",
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
@Composable
fun EnrollmentDialog(
    onDismiss: () -> Unit,
    subjectInfo: GetSubjectLessons?
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Enrollment Details",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                // Pricing Section
                Text(
                    text = "Pricing",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(text = "Price per Lesson: ${subjectInfo?.lessonPrice ?: "Unknown"}")
                Text(text = "Total Price: ${subjectInfo?.lessonPriceAll ?: "Unknown"}")

                Text(
                    text = "other options",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                // Display Other Prices
                subjectInfo?.otherPrices?.forEach { price ->
                    Text(
                        text = "${price.key} Lessons: ${price.value}",
                        fontSize = 14.sp,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }



                Spacer(modifier = Modifier.height(16.dp))

                // Course Dates Section
                Text(
                    text = "Course Dates",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(text = "Start Date: ${subjectInfo?.startDate ?: "Unknown"}")
                Text(text = "End Date: ${subjectInfo?.endDate ?: "Unknown"}")

                Spacer(modifier = Modifier.height(16.dp))

                // Payment Method Section
                Text(
                    text = "Payment Method",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(text = "Payment Method: ${subjectInfo?.paymentMethod ?: "Unknown"}")

                Spacer(modifier = Modifier.height(16.dp))

                // Seats Info Section
                Text(
                    text = "Seats Info",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(text = "Available Seats: ${subjectInfo?.availableSeats ?: "Unknown"}")
                Text(text = "Remaining Seats: ${subjectInfo?.remainingSeats ?: "Unknown"}")

                Spacer(modifier = Modifier.height(16.dp))

                // Close Button
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = { },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
                        shape = RoundedCornerShape(5.dp)
                    ) {
                        Text(
                            text = " continue Enrollment",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Light,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun PreviewLessons() {
    val navController = rememberNavController()
    val lesson = Lesson(
        id = "1",
        name = LanguageContent(en = "Sample Lesson", ar = "درس تجريبي"),
        description = LanguageContent(en = "This is a description", ar = "هذا وصف"),
        startDate = "2024-09-01",
        endDate = "2024-09-30",
        link = "https://example.com",
        subjectId = "sub123",
        createdAt = "2024-08-01",
        updatedAt = "2024-08-15",
        version = 1
    )
    // Pass the mock NavController to your composable
    LessonCard( lesson = lesson)
}