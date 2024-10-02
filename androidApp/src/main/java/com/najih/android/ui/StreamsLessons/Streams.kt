package com.najih.android.ui.StreamsLessons

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.najih.android.api.CreateHttpClient
import com.najih.android.api.subjects.GetStreams
import com.najih.android.dataClasses.Streams
import com.najih.android.dataClasses.StreamsInfo
import com.najih.android.ui.homePage.components.SearchBar
import com.najih.android.ui.uitilis.navbar
import com.najih.android.ui.recordedLessons.EnrollmentButtons
import com.najih.android.ui.uitilis.BottomNavBar
import io.ktor.client.engine.android.Android
import kotlinx.coroutines.launch

@Composable
fun Streams (navController: NavController,subjectId:String,teacherId:String){
    val httpClient = CreateHttpClient(Android)
    val coroutineScope = rememberCoroutineScope()

    // State for managing subject information and dialog visibility
    var streamObjectInfo by remember { mutableStateOf<StreamsInfo?>(null) }
    val showDialog = rememberSaveable { mutableStateOf(false) }
    var subjectName by remember { mutableStateOf("Unknown") }
    var stage by remember { mutableStateOf("") }
    var streamList by remember { mutableStateOf<List<Streams>?>(null) }
    // Asynchronous data fetching
    LaunchedEffect(subjectId) {
        coroutineScope.launch {
            try {
                streamObjectInfo = GetStreams(httpClient, subjectId,teacherId)
                subjectName = streamObjectInfo?.subject?.name?.en ?: "Unknown"
                stage = "${streamObjectInfo?.subject?.level?.en} ${streamObjectInfo?.subject?.classNumber}"
                streamList = streamObjectInfo?.lessons
                Log.d("ApiClient", "Subject info fetched: $streamObjectInfo")
            } catch (e: Exception) {
                Log.e("ApiClientError", "Error fetching subject info", e)
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {navbar(navController) },
        bottomBar = { BottomNavBar(navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SearchBar()
            Text(

                text = subjectName,
                fontSize = 20.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 11.dp, top = 49.dp)
            )
            EnrollmentButtons(navController, stage, showDialog)
            streamList?.map { stream ->
                StreamCard(stream)
            }
        }
    }
}



@Composable
fun StreamCard( stream: Streams) {
    Log.d("ApiClient", "Making GET request to URL: $stream")
    val lessonTitle = stream.name.en
    val lessonDesc = stream.description.en
    val startData = stream.startDate
    val endDate = stream.endDate
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(top = 20.dp)
            .shadow(4.dp, RoundedCornerShape(5.dp))
    ) {
        Card(
            modifier = Modifier.fillMaxSize(),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
            shape = RoundedCornerShape(5.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier.padding(10.dp)
            ) {
                Text(
                    text = lessonTitle,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light
                )
                Text(
                    text = lessonDesc,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier.padding(top = 16.dp)
                )
                Text(
                    text = "startData : $startData",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(top = 11.dp)
                )
                Text(
                    text = "endDate : $endDate",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(top = 11.dp)
                )
            }
        }
    }
}