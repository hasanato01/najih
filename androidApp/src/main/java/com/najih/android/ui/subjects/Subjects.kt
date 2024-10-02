package com.najih.android.ui.subjects


import GetSubjectsResponse
import android.util.Log
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.najih.android.R
import com.najih.android.api.CreateHttpClient
import com.najih.android.api.subjects.getRecordedSubjects
import com.najih.android.api.subjects.getStreamsSubjects
import com.najih.android.dataClasses.StreamsSubjects
import com.najih.android.ui.subjects.components.ClassSection
import com.najih.android.ui.subjects.components.SubjectRow
import com.najih.android.ui.uitilis.BottomNavBar
import com.najih.android.ui.uitilis.navbar
import io.ktor.client.engine.android.Android
import kotlinx.coroutines.launch

@Composable
fun Subjects(navController: NavController, type: String , endPoint:String) {
    val httpClient = CreateHttpClient(Android)
    val coroutineScope = rememberCoroutineScope()

    // State for subjects, class grouping, and level
    var recordedsubjects by remember { mutableStateOf<List<GetSubjectsResponse>>(emptyList()) }
    var streamsSubjects by remember { mutableStateOf<List<StreamsSubjects>>(emptyList()) }
    var groupByRecordedClass by remember {
        mutableStateOf<Map<Int, List<GetSubjectsResponse>>>(
            emptyMap()
        )
    }
    var groupByStreamsClass by remember { mutableStateOf<Map<Int, List<StreamsSubjects>>>(emptyMap()) }
    var level by remember { mutableStateOf("Unknown Level") }

    LaunchedEffect(type) {
        coroutineScope.launch {
            try {
                if (endPoint == "r_subjects") {
                    recordedsubjects = getRecordedSubjects(httpClient, type, endPoint)
                    groupByRecordedClass = recordedsubjects.groupBy { it.classNumber }
                    level = recordedsubjects.firstOrNull()?.level?.en ?: "Unknown Level"
                    Log.d("Subjects", recordedsubjects.toString())
                    Log.d("groupClasses", groupByRecordedClass.toString())
                } else if (endPoint == "t_subjects") {
                    streamsSubjects = getStreamsSubjects(httpClient, type, endPoint)
                    groupByStreamsClass = streamsSubjects.groupBy { it.classNumber }
                    level = streamsSubjects.firstOrNull()?.level?.en ?: "Unknown Level"
                    Log.d("Subjects", StreamsSubjects.toString())
                    Log.d("groupClasses", groupByStreamsClass.toString())
                }

            } catch (e: Exception) {
                Log.e("SubjectFetchError", "Error fetching subjects", e)
            }
        }
    }

    val groupByClass = when (endPoint) {
        "r_subjects" -> groupByRecordedClass
        "t_subjects" -> groupByStreamsClass
        else -> emptyMap()
    }

    // Determine which subjects list to check
    val subjects = when (endPoint) {
        "r_subjects" -> recordedsubjects
        "t_subjects" -> streamsSubjects
        else -> emptyList()
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { navbar(navController) },
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
                    ClassSection("$level Class $classNumber")
                }

                // Mapping the subject data to pairs for each subject row
                val subjectPair = subjects.map {
                    when (it) {
                        is GetSubjectsResponse -> it.name.en to it.id
                        is StreamsSubjects -> it.name.en to it.id
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







