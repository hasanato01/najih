package com.najih.android.ui.subjects


import GetSubjectsResponse
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
fun Subjects(navController: NavController, type: String , stage : String,endPoint:String) {
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
    val level by viewModel.level
    val isLoading by viewModel.isLoading
    val errorMessage by viewModel.errorMessage



    val groupByClass = when (endPoint) {
        "r_subjects" -> groupByRecordedClass
        "t_subjects" -> groupByStreamsClass
        else -> emptyMap()
    }

    // Determine which subjects list to check
    val subjects = when (endPoint) {
        "r_subjects" -> recordedSubjects
        "t_subjects" -> streamsSubjects
        else -> emptyList()
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { Navbar(navController  , backText = stage , titleText = stringResource(R.string.subjects) ) },
        bottomBar = { BottomNavBar(navController) }
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .background(color = Color(0xfff9f9f9))
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(innerPadding),
            contentPadding = PaddingValues(bottom = 16.dp) // Optional bottom padding
        ) {


            // Iterate through the groupByClass map directly
            groupByClass.forEach { (classNumber, subjects) ->
                item {
                    ClassSection( classNumber)
                }

                val subjectPair = subjects.map { subject ->
                    when (subject) {
                        is GetSubjectsResponse -> {
                            if (currentLanguage == "ar") {
                                subject.name.ar to subject.id // Arabic name
                            } else {
                                subject.name.en to subject.id // English name
                            }
                        }
                        is StreamsSubjects -> {
                            if (currentLanguage == "ar") {
                                subject.name.ar to subject.id // Arabic name
                            } else {
                                subject.name.en to subject.id // English name
                            }
                        }
                        else -> "Unknown" to "0"
                    }
                }


                item {
                    SubjectRow(navController, subjectPair, endPoint)
                }
            }

            // Optional: Display a message if no subjects are available
            if (subjects.isEmpty()) {
                item {
                    Text(
                        text = stringResource(R.string.no_subjects_available),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.padding(top = 16.dp, start = 11.dp)
                    )
                }
            }
        }
    }
}







