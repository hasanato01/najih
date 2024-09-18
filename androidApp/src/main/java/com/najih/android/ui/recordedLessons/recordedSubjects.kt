package com.najih.android.ui.recordedLessons


import GetSubjectsResponse
import LanguageContent
import OtherPrice
import android.graphics.Paint.Align
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.najih.android.R
import com.najih.android.api.CreateHttpClient
import com.najih.android.api.subjects.GetLessonsBySubject
import com.najih.android.api.subjects.getSubjects
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
fun RecordedLessons(navController: NavController, type: String) {
    val httpClient = CreateHttpClient(Android)
    val coroutineScope = rememberCoroutineScope()

    // State for subjects, class grouping, and level
    var subjects by remember { mutableStateOf<List<GetSubjectsResponse>>(emptyList()) }
    var groupByClass by remember { mutableStateOf<Map<Int, List<GetSubjectsResponse>>>(emptyMap()) }
    var level by remember { mutableStateOf("Unknown Level") }

    LaunchedEffect(type) {
        coroutineScope.launch {
            try {
                val fetchedSubjects = getSubjects(httpClient, type)
                subjects = fetchedSubjects
                groupByClass = fetchedSubjects.groupBy { it.classNumber }
                level = fetchedSubjects.firstOrNull()?.level?.en ?: "Unknown Level"
                Log.d("LessonsSubjects", fetchedSubjects.toString())
                Log.d("groupClasses", groupByClass.toString())
            } catch (e: Exception) {
                Log.e("SubjectFetchError", "Error fetching subjects", e)
            }
        }
    }

    Column(
        modifier = Modifier
            .background(color = Color(0xfff9f9f9))
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(6.dp)
    ) {
        navbar(navController)
        SearchBar()
        Text(
            text = level,
            fontSize = 20.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.padding(top = 49.dp, start = 11.dp)
        )

        // Iterating through each class group
        groupByClass.forEach { (classNumber, subjects) ->
            ClassSection("Class $classNumber")
            val subjectPair = subjects.map { it.name.en to it.id }
            SubjectRow(navController, subjectPair)
        }

        // Optional: Display a message if no subjects are available
        if (subjects.isEmpty()) {
            Text(
                text = stringResource(R.string.no_subjects_available),
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(top = 16.dp, start = 11.dp)
            )
        }
    }
}


@Composable
fun ClassSection(className: String) {
    Box(
        modifier = Modifier
            .width(150.dp)
            .padding(8.dp)
            .clip(RoundedCornerShape(28.dp))
            .background(Color(0xFFF4BC43))
            .padding(8.dp)
    ) {
        Text(
            text = className,
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Light,
            modifier = Modifier.align(Alignment.Center) // Center the text within the Box
        )
    }

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

    Button(
        onClick = { navController.navigate("subject_lessons/$subjectId") },
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .border(width = 0.5.dp, color = Color.Blue, shape = RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(10.dp) ,
        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
    ) {
        Text(
            text = subject,
            color = Color(0xFF143290),
            fontSize = 10.sp,
            fontWeight = FontWeight.Light
        )
    }
}




//@Preview
//@Composable
//fun recordedSubjectsPreview () {
//    val navController = rememberNavController()
//  RecordedLessons(navController , listOf(
//      GetSubjectsResponse(
//          id = "1",
//          name = LanguageContent("Mathematics" , ""),
//          level = LanguageContent("Intermediate" , ""),
//          lessonCount = 10,
//          startDate = "2024-09-01",
//          endDate = "2024-12-01",
//          availableSeats = 20,
//          remainingSeats = 5,
//          lessonDuration = "1 hour",
//          lessonPrice = "$100",
//          paymentMethod = "Credit Card",
//          lessonPriceAll = "$1000",
//          classNumber = 101,
//          otherPrices = listOf(
//              OtherPrice(1, 20)
//          ),
//          lessons = listOf("lesson1", "lesson2"),
//          createdAt = "2024-08-01T10:00:00Z",
//          updatedAt = "2024-08-15T10:00:00Z",
//          version = 1
//      ),   GetSubjectsResponse(
//          id = "1",
//          name = LanguageContent("Mathematics" , ""),
//          level = LanguageContent("Intermediate" , ""),
//          lessonCount = 10,
//          startDate = "2024-09-01",
//          endDate = "2024-12-01",
//          availableSeats = 20,
//          remainingSeats = 5,
//          lessonDuration = "1 hour",
//          lessonPrice = "$100",
//          paymentMethod = "Credit Card",
//          lessonPriceAll = "$1000",
//          classNumber = 101,
//          otherPrices = listOf(
//              OtherPrice(1, 20)
//          ),
//          lessons = listOf("lesson1", "lesson2"),
//          createdAt = "2024-08-01T10:00:00Z",
//          updatedAt = "2024-08-15T10:00:00Z",
//          version = 1
//      )), )
//}