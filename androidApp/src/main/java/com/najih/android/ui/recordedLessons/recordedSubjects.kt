package com.najih.android.ui.recordedLessons


import GetSubjectsResponse
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.najih.android.api.CreateHttpClient
import com.najih.android.api.subjects.GetLessonsBySubject
import com.najih.android.ui.homePage.components.SearchBar
import com.najih.android.ui.navbar
import com.najih.android.util.GlobalFunctions
import io.ktor.client.engine.android.Android
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.Objects

@Composable
fun RecordedLessons(navController: NavController, resultObjects: List<GetSubjectsResponse>?) {
    Log.d("LessonsSubjects", resultObjects.toString())


    // Grouping the subjects by class
    val groupByClass = resultObjects?.groupBy { it.classNumber }
    val level = resultObjects?.firstOrNull()?.level?.en ?: "Unknown Level"
    Log.d("groupClasses", groupByClass.toString())

    Column(
        modifier = Modifier
            .background(color = Color(0xfff9f9f9))
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(6.dp)
    ) {
        navbar()
        SearchBar()
        Text(
            text = level,
            fontSize = 20.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.padding(top = 49.dp, start = 11.dp)
        )

        // Iterating through each class group
        groupByClass?.forEach { (classNumber, subjects) ->
            ClassSection("Class $classNumber")
            val subjectPair = subjects.map { it.name.en to it.id }
            SubjectRow(navController, subjectPair)
        } ?: run {
            // Optional: Display a message if no subjects are available
            Text(
                text = "No subjects available",
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(top = 16.dp, start = 11.dp)
            )
        }
    }
}


@Composable
fun ClassSection(className: String) {
    Text(
        text = className,
        color = Color.White,
        fontSize = 14.sp,
        fontWeight = FontWeight.Light,
        modifier = Modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(28.dp))
            .background(Color(0xFFF4BC43))
            .padding(8.dp)
    )
}

@Composable
fun SubjectRow(navController: NavController,subjects: List<Pair<String, String>>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Split the list into pairs and create a row for each pair
        subjects.chunked(2).forEach { pair ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                pair.forEach { (subjectName, subjectId) ->
                    SubjectButton(navController, subjectName, subjectId, Modifier.weight(1f))
                }
                // If there's an odd number of subjects, add an empty spacer to balance the row
                if (pair.size < 2) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
fun SubjectButton(
    navController: NavController,
    subject: String,
    subjectId: String,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val httpClient = CreateHttpClient(Android) // Assuming CreateHttpClient is a function that returns an HttpClient instance

    Button(
        onClick = {
            coroutineScope.launch {
                try {
                    // Perform the HTTP request to fetch subject info
                    val subjectInfo = GetLessonsBySubject(httpClient, subjectId)

                    // Check if subjectInfo is not null and serialize it
                    if (subjectInfo != null) {
                        val serializedSubjectInfo = Json.encodeToString(subjectInfo)
                        val encodedSubjectInfo = Uri.encode(serializedSubjectInfo) // Encode for URL safety
                        navController.navigate("subject_lessons/$encodedSubjectInfo")
                    } else {
                        Log.d("SubjectLessons", "No subject info found for id: $subjectId")
                    }

                } catch (e: Exception) {
                    Log.e("SubjectInfoError", "Error fetching subject info", e)
                }
            }
        },
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White)
            .padding(10.dp)
    ) {
        Text(
            text = subject,
            color = Color(0xFF143290),
            fontSize = 10.sp,
            fontWeight = FontWeight.Light
        )
    }
}




@Preview
@Composable
fun recordedSubjectsPreview () {
//    RecordedLessons()
}