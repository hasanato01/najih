package com.najih.android.ui.StreamsLessons

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.najih.android.R
import com.najih.android.api.CreateHttpClient
import com.najih.android.api.subjects.GetTeachersBySubject
import com.najih.android.dataClasses.StreamsSubjectWithTeachers
import com.najih.android.dataClasses.Teacher
import com.najih.android.ui.uitilis.BottomNavBar
import com.najih.android.ui.uitilis.Navbar
import com.najih.android.util.GlobalFunctions
import io.ktor.client.engine.android.Android
import kotlinx.coroutines.launch

@Composable
fun Teachers (navController: NavController,subjectId:String){
    val httpClient = CreateHttpClient(Android)
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    // State for managing subject information and dialog visibility
    var subjectInfo by remember { mutableStateOf<StreamsSubjectWithTeachers?>(null) }
    val showDialog = rememberSaveable { mutableStateOf(false) }
    val currentLanguage by remember { mutableStateOf(GlobalFunctions.getUserLanguage(context) ?: "ar") }
    var subjectName by remember { mutableStateOf("Unknown") }
    var stage by remember { mutableStateOf("") }
    var teacherList by remember { mutableStateOf<List<Teacher>?>(null) }

    // Asynchronous data fetching
    LaunchedEffect(subjectId) {
        coroutineScope.launch {
            try {
                subjectInfo = GetTeachersBySubject(httpClient, subjectId)
                subjectName = when (currentLanguage) {
                    "ar" -> subjectInfo?.name?.ar ?: "غير معروف"
                    else -> subjectInfo?.name?.en ?: "Unknown"
                }
                stage = when (currentLanguage) {
                    "ar" -> "${subjectInfo?.level?.ar ?: "غير معروف"} "
                    else -> "${subjectInfo?.level?.en ?: "Unknown"} "
                }
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
            teacherList?.map { teacher ->
                TeacherCard(navController, teacher, subjectId)
            }


        }
    }
}

@Composable
fun TeacherCard(navController: NavController, teacher: Teacher, subjectId: String) {
    Log.d("ApiClient", "Making GET request to URL: $teacher")
    val teacherName = teacher.name
    val teacherId = teacher.id
    val schoolName = teacher.schoolName
    val experience = teacher.experience
    val subjects = teacher.subjects
    val address = teacher.address
    val phoneNumber = teacher.phoneNumber

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(vertical = 10.dp, horizontal = 16.dp)
            .shadow(6.dp, RoundedCornerShape(8.dp))
            .clickable {
                navController.navigate("streams/${subjectId}/${teacherId}")
            }
    ) {
        Card(
            modifier = Modifier.fillMaxSize(),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 6.dp),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = teacherName,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Text(
                    text = stringResource(R.string.school, schoolName),
                    color = Color.DarkGray
                )

                Text(
                    text = stringResource(R.string.experience, experience),
                    color = Color.DarkGray
                )

                Text(
                    text = stringResource(R.string.TeacherSubjects, subjects),
                    color = Color.DarkGray
                )

                Text(
                    text = stringResource(R.string.address, address),
                    color = Color.DarkGray
                )

                Text(
                    text = stringResource(R.string.phone, phoneNumber),
                    color = Color.DarkGray
                )
            }
        }
    }
}
