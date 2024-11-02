package com.najih.android.ui.recordedLessons

import GetSubjectLessons
import Lesson
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

    // States
    var subjectInfo by remember { mutableStateOf<GetSubjectLessons?>(null) }
    var subjectName by remember { mutableStateOf("Unknown") }
    var stage by remember { mutableStateOf("") }
    var lessonsList by remember { mutableStateOf<List<Lesson>?>(null) }
    val currentLanguage by remember { mutableStateOf(GlobalFunctions.getUserLanguage(context) ?: "en") }
    var isCheckableMode by remember { mutableStateOf(false) }
    var showConfirmationDialog by remember { mutableStateOf(false) }

    // Selected lessons data structures
    val purchasedLessons = remember { mutableStateMapOf<String, MutableList<String>>() }
    val recorderLessonsIds = remember { mutableStateListOf<String>() }
    val recorderLessons = remember { mutableStateListOf<Lesson>() }
    val selectedLessons = remember { mutableStateMapOf<String, Boolean>() }

    // Data fetching
    LaunchedEffect(subjectId) {
        coroutineScope.launch {
            try {
                subjectInfo = GetLessonsBySubject(httpClient, subjectId)
                subjectName = when (currentLanguage) {
                    "ar" -> subjectInfo?.name?.ar ?: "غير معروف"
                    else -> subjectInfo?.name?.en ?: "Unknown"
                }
                stage = when (currentLanguage) {
                    "ar" -> "${subjectInfo?.level?.ar ?: "غير معروف"} "
                    else -> "${subjectInfo?.level?.en ?: "Unknown"} "
                }
                lessonsList = subjectInfo?.listoflessons
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White)
        ) {
            // Static SubjectInfo Section (not part of LazyColumn)
            SubjectInfo(
                subjectInfo = subjectInfo,
                isCheckableMode = isCheckableMode,
                showConfirmationDialog = showConfirmationDialog,
                onToggleCheckableMode = { newState -> isCheckableMode = newState },
                onToggleConfirmDialog = { newState -> showConfirmationDialog = newState },
                selectedLessons = selectedLessons
            )
            if (isCheckableMode) {
                Text(
                    text = "Please Select lessons",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding( 16.dp)
                )
            }

            // Confirmation Dialog
            if (showConfirmationDialog) {
                UploadFileDialog(
                    onDismiss = { showConfirmationDialog = false },
                    purchasedLessons,
                    recorderLessonsIds,
                    recorderLessons,
                    context,
                    httpClient
                )
            }
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                itemsIndexed(lessonsList ?: emptyList()) { index, lesson ->
                    val isChecked = selectedLessons[lesson.id] == true
                    LessonCard(
                                lesson = lesson,
                                lessonNumber = index + 1,
                                isCheckableMode = isCheckableMode,
                                isChecked = isChecked,
                                onCheckedChange = { isSelected ->
                                    if (isSelected) {
                                        selectedLessons[lesson.id] = true

                                        // Add lesson ID to purchasedLessons map
                                        val subjectLessonIds = purchasedLessons.getOrPut(subjectId) { mutableListOf() }
                                        if (!subjectLessonIds.contains(lesson.id)) {
                                            subjectLessonIds.add(lesson.id)
                                        }

                                        // Add lesson ID to recorderLessonsIds list if not already present
                                        if (!recorderLessonsIds.contains(lesson.id)) {
                                            recorderLessonsIds.add(lesson.id)
                                        }

                                        // Add full lesson details to recorderLessons if not already present
                                        if (!recorderLessons.any { it.id == lesson.id }) {
                                            recorderLessons.add(lesson)
                                        }

                                    } else {
                                        selectedLessons.remove(lesson.id)

                                        // Remove lesson ID from purchasedLessons map
                                        purchasedLessons[subjectId]?.remove(lesson.id)
                                        if (purchasedLessons[subjectId]?.isEmpty() == true) {
                                            purchasedLessons.remove(subjectId)
                                        }

                                        // Remove lesson ID from recorderLessonsIds list
                                        recorderLessonsIds.remove(lesson.id)

                                        // Remove full lesson details from recorderLessons
                                        recorderLessons.removeAll { it.id == lesson.id }
                                    }

                                    // Logging for debugging
                                    Log.d("LessonCard", "Purchased Lessons: $purchasedLessons")
                                    Log.d("LessonCard", "Recorder Lesson IDs: $recorderLessonsIds")
                                    Log.d("LessonCard", "Recorder Lessons: $recorderLessons")
                                }



                            )

                }
            }
        }
    }

}

