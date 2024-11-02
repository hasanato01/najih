package com.najih.android.ui.homePage.components

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.najih.android.R
import com.najih.android.util.GlobalFunctions
import io.ktor.client.HttpClient

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.najih.android.viewModels.Teachers.TeachersViewModel
import com.najih.android.viewModels.Teachers.TeachersViewModelFactory

@Composable
fun HomePageTeachers(navController: NavController,httpClient: HttpClient, context: Context) {
    val viewModel: TeachersViewModel = viewModel(factory = TeachersViewModelFactory(httpClient, context))
    val teachersList by viewModel.teachersList.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val currentLanguage by remember { mutableStateOf(GlobalFunctions.getUserLanguage(context) ?: "en") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Our Teachers",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.align(Alignment.CenterVertically)
            )

            TextButton(onClick = { navController.navigate("our_teachers")}) {
                Text(
                    text = "See All →",
                    style = MaterialTheme.typography.labelMedium,
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else if (teachersList.isEmpty()) {
            Text("No teachers available", modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                val limitedTeachersListSize = minOf(5, teachersList.size)
                items(limitedTeachersListSize) { index ->
                    val teacher = teachersList[index]

                    val name = if (currentLanguage == "ar") teacher.name else teacher.name
                    val schoolName = if (currentLanguage == "ar") teacher.schoolName else teacher.schoolName
                    val levels = if (currentLanguage == "ar") teacher.levels.ar else teacher.levels.en
                    val subjects = teacher.subjects
                    val secureImageUrl = teacher.image?.url?.replace("http://", "https://")

                    val imagePainter = if (secureImageUrl != null) {
                        rememberAsyncImagePainter(secureImageUrl)
                    } else {
                        painterResource(R.drawable.submit)
                    }

                    TeacherCard(
                        name = name,
                        schoolName = schoolName,
                        level = levels,
                        experience = teacher.experience,
                        subjects = subjects,
                        image = imagePainter,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }
}
