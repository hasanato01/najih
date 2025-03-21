package com.najih.android.ui.recordedLessons

import GetSubjectLessons
import Lesson
import android.util.Log
import android.widget.Toast
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
import androidx.compose.runtime.mutableDoubleStateOf
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.najih.android.R
import com.najih.android.api.CreateHttpClient
import com.najih.android.api.auth.checkJWT
import com.najih.android.api.subjects.GetLessonsBySubject
import com.najih.android.ui.uitilis.BottomNavBar
import com.najih.android.ui.uitilis.Navbar
import com.najih.android.util.GlobalFunctions
import com.najih.android.util.YouTubePlayerDialog
import com.najih.android.util.extractYouTubeVideoId
import io.ktor.client.engine.android.Android
import kotlinx.coroutines.launch
import kotlin.math.log

@Composable
fun Lessons(navController: NavController, subjectId: String) {
    val context = LocalContext.current
    val httpClient = CreateHttpClient(Android)
    val coroutineScope = rememberCoroutineScope()

    // States
    var subjectInfo by remember { mutableStateOf<GetSubjectLessons?>(null) }
    var subjectName by remember { mutableStateOf("Unknown") }
    var subjectNameAR by remember { mutableStateOf("Unknown") }
    var subjectClass by remember { mutableStateOf("Unknown") }
    var lessonsPrice by remember { mutableDoubleStateOf(0.0) }
    var stage by remember { mutableStateOf("") }
    var lessonsList by remember { mutableStateOf<List<Lesson>?>(null) }
    val currentLanguage by remember { mutableStateOf(GlobalFunctions.getUserLanguage(context) ?: "en") }
    var isCheckableMode by remember { mutableStateOf(false) }
    var showConfirmationDialog by remember { mutableStateOf(false) }
    val userPurchasedLessons = GlobalFunctions.getUserInfo(context).purchasedLessons
    val purchasedLessons = remember { mutableStateMapOf<String, MutableList<String>>() }
    val recorderLessonsIds = remember { mutableStateListOf<String>() }
    val recorderLessons = remember { mutableStateListOf<Lesson>() }
    val selectedLessons = remember { mutableStateMapOf<String, Boolean>() }
    var previewVideoId by remember { mutableStateOf<String?>(null) }

    Log.d("ApiClient",userPurchasedLessons.toString())
    LaunchedEffect(subjectId) {
        coroutineScope.launch {
            try {
                val jwtResponse = checkJWT(httpClient, context)
                subjectInfo = GetLessonsBySubject(httpClient, subjectId)
                Log.d("ApiClient", "$subjectInfo")
                subjectNameAR = subjectInfo?.name?.ar.toString()
                subjectClass = "${subjectInfo?.level?.ar ?: ""} ${subjectInfo?.classNumber ?: ""}".trim()
                lessonsPrice = subjectInfo?.lessonPrice?.toDoubleOrNull() ?: 0.0

                subjectName = when (currentLanguage) {
                    "ar" -> subjectInfo?.name?.ar ?: "غير معروف"
                    else -> subjectInfo?.name?.en ?: "Unknown"
                }
                stage = when (currentLanguage) {
                    "ar" -> "${subjectInfo?.level?.ar ?: "غير معروف"} "
                    else -> "${subjectInfo?.level?.en ?: "Unknown"} "
                }
                lessonsList = subjectInfo?.listoflessons
                Log.d("ApiClient", "userPurchasedStreams: ($userPurchasedLessons)")
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
            // SubjectInfo Section (not part of LazyColumn)
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
                    text = stringResource(R.string.please_select_lessons),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(16.dp)
                )
            }

            // Confirmation Dialog
            if (showConfirmationDialog) {
                UploadFileDialog(
                    onDismiss = { showConfirmationDialog = false },
                    purchasedLessons,
                    recorderLessonsIds,
                    recorderLessons,
                    subjectNameAR,
                    subjectClass,
                    lessonsPrice,
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
                    val isPurchased = userPurchasedLessons.any {
                        it[subjectId]?.contains(lesson.id) == true
                    }


                    LessonCard(
                        lesson = lesson,
                        lessonNumber = index + 1,
                        isCheckableMode = isCheckableMode,
                        isChecked = isChecked,
                        isPurchased = isPurchased,
                        onCheckedChange = { isSelected ->
                            if (isSelected) {
                                selectedLessons[lesson.id] = true
                                val subjectLessonIds = purchasedLessons.getOrPut(subjectId) { mutableListOf() }
                                if (!subjectLessonIds.contains(lesson.id)) {
                                    subjectLessonIds.add(lesson.id)
                                }
                                if (!recorderLessonsIds.contains(lesson.id)) {
                                    recorderLessonsIds.add(lesson.id)
                                }
                                if (!recorderLessons.any { it.id == lesson.id }) {
                                    recorderLessons.add(lesson)
                                }
                            } else {
                                selectedLessons.remove(lesson.id)
                                purchasedLessons[subjectId]?.remove(lesson.id)
                                if (purchasedLessons[subjectId]?.isEmpty() == true) {
                                    purchasedLessons.remove(subjectId)
                                }
                                recorderLessonsIds.remove(lesson.id)
                                recorderLessons.removeAll { it.id == lesson.id }
                            }
                        },
                        onLessonPurchasedClick = {
                            Toast.makeText(context, "This lesson is already purchased", Toast.LENGTH_SHORT).show()
                        },
                        onPreviewLessonClick = { url ->
                            Log.d("PreviewLesson", "Lesson preview clicked. URL: $url")
                            try {
                                val extractedVideoId = extractYouTubeVideoId(url)
                                Log.d("PreviewLesson", "Extracted Video ID: $extractedVideoId")

                                if (extractedVideoId.isNullOrEmpty()) {
                                    Toast.makeText(context, "Error extracting video ID", Toast.LENGTH_SHORT).show()
                                    return@LessonCard
                                }

                                previewVideoId = extractedVideoId
                            } catch (e: Exception) {
                                Log.e("PreviewLesson", "Error extracting video ID", e)
                                Toast.makeText(context, "Failed to load video", Toast.LENGTH_SHORT).show()
                            }
                        }
                    )
                }
            }
        }
    }

    // Show VideoPlayerDialog if previewVideoUrl is not null
    previewVideoId?.let { videoId ->
        if (videoId.isNotEmpty()) {
            YouTubePlayerDialog(
                videoId = videoId,
                onDismiss = { previewVideoId = null }
            )
        } else {
            Log.e("PreviewLesson", "Invalid Video ID: $videoId")
        }
    }
}

