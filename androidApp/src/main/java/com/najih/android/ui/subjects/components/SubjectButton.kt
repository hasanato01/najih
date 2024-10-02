package com.najih.android.ui.subjects.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun SubjectButton(
    navController: NavController,
    subject: String,
    subjectId: String,
    endPoint:String,
    modifier: Modifier = Modifier
) {

    Button(
        onClick = {
            if (endPoint == "r_subjects") {
                // Navigate to subject lessons
                navController.navigate("subject_lessons/$subjectId")
            } else if (endPoint == "t_subjects") {
                // Navigate to subject teachers
                navController.navigate("subject_teachers/$subjectId")
            }
        },
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .border(width = 0.5.dp,  color = Color(0xb8b8b8FF), shape = RoundedCornerShape(8.dp))
            .background(Color.White)
            .padding(5.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            color = Color.Black,
            text = subject,
            fontSize = 20.sp,

            )
    }
}


