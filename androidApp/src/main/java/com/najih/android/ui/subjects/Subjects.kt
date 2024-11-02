package com.najih.android.ui.subjects


import GetSubjectsResponse
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.najih.android.R
import com.najih.android.viewModels.Subjects.SubjectsViewModelFactory
import com.najih.android.api.CreateHttpClient
import com.najih.android.dataClasses.StreamsSubjects
import com.najih.android.ui.subjects.components.ClassSection
import com.najih.android.ui.subjects.components.SubjectRow
import com.najih.android.ui.uitilis.BottomNavBar
import com.najih.android.ui.uitilis.Navbar
import com.najih.android.util.GlobalFunctions
import com.najih.android.viewModels.Subjects.SubjectsViewModel
import io.ktor.client.engine.android.Android

@Composable
fun Subjects(navController: NavController, type: String, stage: String, endPoint: String) {
    val context = LocalContext.current
    val httpClient = CreateHttpClient(Android)
    val viewModel: SubjectsViewModel = viewModel(factory = SubjectsViewModelFactory(httpClient))
    val currentLanguage by remember { mutableStateOf(GlobalFunctions.getUserLanguage(context) ?: "en") }

    LaunchedEffect(type) {
        viewModel.fetchSubjects(type, endPoint)
    }

    val recordedSubjects by viewModel.recordedSubjects
    val streamsSubjects by viewModel.streamsSubjects
    val groupByRecordedClass by viewModel.groupByRecordedClass
    val groupByStreamsClass by viewModel.groupByStreamsClass
    val isLoading by viewModel.isLoading // Observe loading state

    val groupByClass = when (endPoint) {
        "r_subjects" -> groupByRecordedClass
        "t_subjects" -> groupByStreamsClass
        else -> emptyMap()
    }

    val subjects = when (endPoint) {
        "r_subjects" -> recordedSubjects
        "t_subjects" -> streamsSubjects
        else -> emptyList()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { Navbar(navController, backText = stage, titleText = stringResource(R.string.subjects)) },
        bottomBar = { BottomNavBar(navController) }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xfff9f9f9))
                .padding(innerPadding)
        ) {
            // Loading Indicator
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(40.dp)
                        .padding(8.dp),
                    color = colorResource(id = R.color.secondColor),
                    strokeWidth = 4.dp
                )
            } else if (subjects.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.books),
                        contentDescription = stringResource(R.string.no_subjects_available),
                        modifier = Modifier.size(100.dp)
                    )
                    Text(
                        text = stringResource(R.string.no_subjects_available),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    groupByClass.forEach { (classNumber, subjects) ->
                        item {
                            ClassSection(classNumber)
                        }

                        val subjectPair = subjects.map { subject ->
                            when (subject) {
                                is GetSubjectsResponse -> {
                                    if (currentLanguage == "ar") {
                                        subject.name.ar to subject.id
                                    } else {
                                        subject.name.en to subject.id
                                    }
                                }
                                is StreamsSubjects -> {
                                    if (currentLanguage == "ar") {
                                        subject.name.ar to subject.id
                                    } else {
                                        subject.name.en to subject.id
                                    }
                                }
                                else -> "Unknown" to "0"
                            }
                        }

                        item {
                            SubjectRow(navController, subjectPair, endPoint)
                        }
                    }
                }
            }
        }
    }


}

