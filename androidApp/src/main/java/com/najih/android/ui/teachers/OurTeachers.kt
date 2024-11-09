package com.najih.android.ui.teachers

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.najih.android.util.GlobalFunctions
import com.najih.android.viewModels.Teachers.TeachersViewModel
import com.najih.android.viewModels.Teachers.TeachersViewModelFactory
import io.ktor.client.HttpClient
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.najih.android.ui.homePage.components.TeacherCard
import com.najih.android.ui.uitilis.BottomNavBar
import com.najih.android.ui.uitilis.Navbar

@Composable
fun OurTeachers(
    navController: NavController,
    httpClient: HttpClient,
    context: Context
) {
    val viewModel: TeachersViewModel = viewModel(factory = TeachersViewModelFactory(httpClient, context))
    val teachersList by viewModel.teachersList.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val currentLanguage by remember { mutableStateOf(GlobalFunctions.getUserLanguage(context) ?: "en") }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { Navbar(navController, backText = "Teachers", titleText = "Our Teachers") },
        bottomBar = { BottomNavBar(navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            } else if (teachersList.isEmpty()) {
                Text("No teachers available", modifier = Modifier.align(Alignment.CenterHorizontally))
            } else {
                // Display items in two columns
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2), // Display 2 items per row
                    contentPadding = PaddingValues(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(teachersList) { teacher ->
                        val name = if (currentLanguage == "ar") teacher.name else teacher.name
                        val schoolName = if (currentLanguage == "ar") teacher.schoolName else teacher.schoolName
                        val levels = teacher.levels.map {
                            if (currentLanguage == "ar") it.ar else it.en
                        }

                        val subjects = teacher.subjects
                        val secureImageUrl = teacher.image?.url?.replace("http://", "https://")

                        TeacherCard(
                            name = name,
                            schoolName = schoolName,
                            level = levels,
                            experience = teacher.experience,
                            subjects = subjects,
                            image = rememberAsyncImagePainter(secureImageUrl),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        )
                    }
                }
            }
        }
    }
}
