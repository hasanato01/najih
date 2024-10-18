package com.najih.android.ui.recordedLessons

import GetSubjectLessons
import Lesson
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.najih.android.api.CreateHttpClient
import com.najih.android.api.subjects.GetLessonsBySubject
import com.najih.android.ui.uitilis.BottomNavBar
import com.najih.android.ui.uitilis.Navbar
import com.najih.android.util.GlobalFunctions
import io.ktor.client.engine.android.Android
import kotlinx.coroutines.launch

@Composable
fun Lessons(navController: NavController, subjectId: String) {
    val context = LocalContext.current
    val httpClient = CreateHttpClient(Android)
    val coroutineScope = rememberCoroutineScope()

    // State for managing subject information and dialog visibility
    var subjectInfo by remember { mutableStateOf<GetSubjectLessons?>(null) }
    var subjectName by remember { mutableStateOf("Unknown") }
    var stage by remember { mutableStateOf("") }
    var lessonsList by remember { mutableStateOf<List<Lesson>?>(null) }
    val currentLanguage by remember { mutableStateOf(GlobalFunctions.getUserLanguage(context) ?: "en") }

    // Asynchronous data fetching
    LaunchedEffect(subjectId) {
        coroutineScope.launch {
            try {
                subjectInfo = GetLessonsBySubject(httpClient, subjectId)

                // Get localized subject name and stage
                subjectName = when (currentLanguage) {
                    "ar" -> subjectInfo?.name?.ar ?: "غير معروف" // Replace with your Arabic name property
                    else -> subjectInfo?.name?.en ?: "Unknown"
                }

                stage = when (currentLanguage) {
                    "ar" -> "${subjectInfo?.level?.ar ?: "غير معروف"} "
                    else -> "${subjectInfo?.level?.en ?: "Unknown"} "
                }

                lessonsList = subjectInfo?.listoflessons
                Log.d("ApiClient", "Subject info fetched: $subjectInfo")
            } catch (e: Exception) {
                Log.e("ApiClientError", "Error fetching subject info", e)
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { Navbar(navController, backText = stage, titleText = subjectName) },
        bottomBar = { BottomNavBar(navController) }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(16.dp) // Optional: Add padding for better spacing
        ) {
            // The first item will be your SubjectInfo card
            item {
                SubjectInfo(subjectInfo = subjectInfo)
            }

            // The remaining items are lesson cards
            itemsIndexed(lessonsList ?: emptyList()) { index, lesson ->
                // Passing both the index and the lesson to LessonCard
                LessonCard(lesson = lesson, lessonNumber = index + 1) // Index is 0-based, so add 1
            }
        }
    }
}





