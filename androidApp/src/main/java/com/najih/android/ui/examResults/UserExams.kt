package com.najih.android.ui.examResults

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.najih.android.api.exams.getUserExams
import com.najih.android.dataClasses.SubmitExamRequest
import com.najih.android.ui.homePage.components.SearchBar
import com.najih.android.ui.uitilis.BottomNavBar
import com.najih.android.ui.uitilis.HomeNavbar
import com.najih.android.ui.uitilis.Navbar
import io.ktor.client.HttpClient


@Composable
fun UserExams (navController: NavController, httpClient: HttpClient, context: Context) {

    // State to hold the fetched exams
    var examResults by remember { mutableStateOf<List<SubmitExamRequest>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Fetch exams when the composable is displayed
    LaunchedEffect(Unit) {
        try {
            val fetchedExamResults = getUserExams(httpClient, context)
            examResults = fetchedExamResults
        } catch (e: Exception) {
            errorMessage = "Error fetching exam Results"
            Log.e("ExamsError", "Failed to fetch exams", e)
        } finally {
            isLoading = false
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {Navbar(navController  , backText = "submitted Exams" , titleText = "My Exams" ) },
        bottomBar = { BottomNavBar(navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .background(Color(0xfff9f9f9))
                .fillMaxSize()
                .padding(innerPadding)
        ) {


//            SearchBar()
            Spacer(modifier = Modifier.height(16.dp))

            if (isLoading) {
                Text("Loading exams...", modifier = Modifier.padding(top = 16.dp))
            } else if (errorMessage != null) {
                Text(errorMessage ?: "Unknown error", modifier = Modifier.padding(top = 16.dp))
            } else if (examResults.isEmpty()) {
                Text("No exams available", modifier = Modifier.padding(top = 16.dp))
            } else {
                // Render the list of exams using LazyColumn
                Text(
                    text = "My Exams",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(top = 16.dp, start = 11.dp)
                )

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    items(examResults) { examResult ->
                        ExamResultsCard(navController, examResult)

                    }
                }
            }
        }
    }
}