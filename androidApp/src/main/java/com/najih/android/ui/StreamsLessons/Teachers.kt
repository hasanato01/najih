package com.najih.android.ui.StreamsLessons

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import com.najih.android.api.subjects.GetTeachersBySubject
import com.najih.android.dataClasses.StreamsSubjectWithTeachers
import com.najih.android.dataClasses.Teacher
import com.najih.android.ui.homePage.components.SearchBar
import com.najih.android.ui.uitilis.BottomNavBar
import com.najih.android.ui.uitilis.HomeNavbar
import com.najih.android.ui.uitilis.Navbar
import io.ktor.client.engine.android.Android
import kotlinx.coroutines.launch

@Composable
fun Teachers (navController: NavController,subjectId:String){
    val httpClient = CreateHttpClient(Android)
    val coroutineScope = rememberCoroutineScope()

    // State for managing subject information and dialog visibility
    var subjectInfo by remember { mutableStateOf<StreamsSubjectWithTeachers?>(null) }
    val showDialog = rememberSaveable { mutableStateOf(false) }
    var subjectName by remember { mutableStateOf("Unknown") }
    var stage by remember { mutableStateOf("") }
    var teacherList by remember { mutableStateOf<List<Teacher>?>(null) }

    // Asynchronous data fetching
    LaunchedEffect(subjectId) {
        coroutineScope.launch {
            try {
                subjectInfo = GetTeachersBySubject(httpClient, subjectId)
                subjectName = subjectInfo?.name?.en ?: "Unknown"
                stage = "${subjectInfo?.level?.en} ${subjectInfo?.classNumber}"
                teacherList = subjectInfo?.teachersIds
                Log.d("ApiClient", "Subject info fetched: $subjectInfo")
            } catch (e: Exception) {
                Log.e("ApiClientError", "Error fetching subject info", e)
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { Navbar(navController  , backText = stage , titleText = subjectName ) },
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
            teacherList?.map { teacher ->
                TeacherCard(navController, teacher, subjectId)
            }


        }
    }
}

@Composable
fun TeacherCard(navController: NavController, teacher: Teacher,subjectId: String) {
    Log.d("ApiClient", "Making GET request to URL: $teacher")
    val teacherName = teacher.name
    val teacherId = teacher.id

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(top = 20.dp)
            .shadow(4.dp, RoundedCornerShape(5.dp))
            .clickable {
                navController.navigate("streams/${subjectId}/${teacherId}")
            }
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
                    text = teacherName,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light
                )
//                Text(
//                    text = lessonDesc,
//                    fontSize = 10.sp,
//                    fontWeight = FontWeight.Light,
//                    modifier = Modifier.padding(top = 16.dp)
//                )
//                Text(
//                    text = "startData : $startData",
//                    fontSize = 10.sp,
//                    fontWeight = FontWeight.Medium,
//                    modifier = Modifier.padding(top = 11.dp)
//                )
//                Text(
//                    text = "endDate : $endDate",
//                    fontSize = 10.sp,
//                    fontWeight = FontWeight.Medium,
//                    modifier = Modifier.padding(top = 11.dp)
//                )
            }
        }
    }
}