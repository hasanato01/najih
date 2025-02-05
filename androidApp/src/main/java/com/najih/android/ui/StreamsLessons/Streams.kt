package com.najih.android.ui.StreamsLessons

import Lesson
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.najih.android.R
import com.najih.android.api.CreateHttpClient
import com.najih.android.api.subjects.GetStreams
import com.najih.android.dataClasses.Streams
import com.najih.android.dataClasses.StreamsInfo
import com.najih.android.ui.homePage.components.SearchBar
import com.najih.android.ui.recordedLessons.LessonCard
import com.najih.android.ui.recordedLessons.SubjectInfo
import com.najih.android.ui.recordedLessons.UploadFileDialog
import com.najih.android.ui.uitilis.BottomNavBar
import com.najih.android.ui.uitilis.HomeNavbar
import com.najih.android.util.GlobalFunctions
import com.najih.android.util.VideoPlayerDialog
import io.ktor.client.engine.android.Android
import kotlinx.coroutines.launch

@Composable
fun Streams (navController: NavController,subjectId:String,teacherId:String){
    val httpClient = CreateHttpClient(Android)
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val currentLanguage by remember { mutableStateOf(GlobalFunctions.getUserLanguage(context) ?: "ar") }

    // State for managing subject information and dialog visibility
    var streamObjectInfo by remember { mutableStateOf<StreamsInfo?>(null) }
    var subjectName by remember { mutableStateOf("Unknown") }
    var subjectNameAR by remember { mutableStateOf("Unknown") }
    var subjectClass by remember { mutableStateOf("Unknown") }
    var lessonsPrice by remember { mutableDoubleStateOf(0.0) }
    var stage by remember { mutableStateOf("") }
    var streamList by remember { mutableStateOf<List<Streams>?>(null) }
    var isCheckableMode by remember { mutableStateOf(false) }
    var showConfirmationDialog by remember { mutableStateOf(false) }
    val userPurchasedStreams = GlobalFunctions.getUserInfo(context).purchasedLessons
    val purchasedStreams = remember { mutableStateMapOf<String, MutableList<String>>() }
    val recorderStreamIds = remember { mutableStateListOf<String>() }
    val recorderStreams = remember { mutableStateListOf<Streams>() }
    val selectedStreams = remember { mutableStateMapOf<String, Boolean>() }
    var previewVideoUrl by remember { mutableStateOf<String?>(null) }
    // Asynchronous data fetching
    LaunchedEffect(subjectId) {
        coroutineScope.launch {
            try {
                streamObjectInfo = GetStreams(httpClient, subjectId,teacherId)
                subjectNameAR = streamObjectInfo?.subject?.name?.ar.toString()
                subjectClass = "${streamObjectInfo?.subject?.level?.ar ?: ""} ${streamObjectInfo?.subject?.classNumber ?: ""}".trim()
                lessonsPrice = streamObjectInfo?.subject?.lessonPrice?.toDoubleOrNull() ?: 0.0
                subjectName = when (currentLanguage) {
                    "ar" -> streamObjectInfo?.subject?.name?.ar ?: "غير معروف"
                    else -> streamObjectInfo?.subject?.name?.en ?: "Unknown"
                }
                stage = when (currentLanguage) {
                    "ar" -> "${streamObjectInfo?.subject?.level?.ar ?: "غير معروف"} "
                    else -> "${streamObjectInfo?.subject?.level?.en ?: "Unknown"} "
                }
                streamList = streamObjectInfo?.lessons
                Log.d("ApiClient", "Subject info fetched: ($streamObjectInfo)")
                Log.d("ApiClient", "userPurchasedStreams: ($userPurchasedStreams)")
            } catch (e: Exception) {
                Log.e("ApiClientError", "Error fetching subject info", e)
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { HomeNavbar(navController) },
        bottomBar = { BottomNavBar(navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // SubjectInfo Section (not part of LazyColumn)
            streamObjectInfo?.let {
                StreamsInfo(
                    subjectInfo = it.subject,
                    isCheckableMode = isCheckableMode,
                    showConfirmationDialog = showConfirmationDialog,
                    onToggleCheckableMode = { newState -> isCheckableMode = newState },
                    onToggleConfirmDialog = { newState -> showConfirmationDialog = newState },
                    selectedLessons = selectedStreams
                )

            }
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
                UploadFileForStreamsDialog(
                    onDismiss = { showConfirmationDialog = false },
                    purchasedStreams,
                    recorderStreamIds,
                    recorderStreams,
                    subjectNameAR,
                    subjectClass,
                    subjectId,
                    teacherId,
                    lessonsPrice,
                    context,
                    httpClient
                )
            }
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                itemsIndexed(streamList ?: emptyList()) { index, stream ->
                    val isChecked = selectedStreams[stream.id] == true
                    val isPurchased = userPurchasedStreams.any {
                        it[subjectId]?.contains(stream.id) == true
                    }


                    StreamCard(
                        stream = stream,
                        isCheckableMode = isCheckableMode,
                        isChecked = isChecked,
                        isPurchased = isPurchased,
                        onCheckedChange = { isSelected ->
                            if (isSelected) {
                                selectedStreams[stream.id] = true
                                val subjectStreamIds = purchasedStreams.getOrPut(subjectId) { mutableListOf() }
                                if (!subjectStreamIds.contains(stream.id)) {
                                    subjectStreamIds.add(stream.id)
                                }
                                if (!recorderStreamIds.contains(stream.id)) {
                                    recorderStreamIds.add(stream.id)
                                }
                                if (!recorderStreams.any { it.id == stream.id }) {
                                    recorderStreams.add(stream)
                                }
                            } else {
                                selectedStreams.remove(stream.id)
                                purchasedStreams[subjectId]?.remove(stream.id)
                                if (purchasedStreams[subjectId]?.isEmpty() == true) {
                                    purchasedStreams.remove(subjectId)
                                }
                                recorderStreamIds.remove(stream.id)
                                recorderStreams.removeAll { it.id == stream.id }
                            }
                        },
                        onLessonPurchasedClick = {
                            Toast.makeText(context, "This stream is already purchased", Toast.LENGTH_SHORT).show()
                        },
                        onPreviewLessonClick = { url -> previewVideoUrl = url } // Set the preview URL to show dialog
                    )
                }
            }

        }
    }
    previewVideoUrl?.let { url ->
        VideoPlayerDialog(
            videoUrl = url,
            onDismiss = { previewVideoUrl = null } // Dismiss dialog by resetting previewVideoUrl
        )
    }
}



