package com.najih.android.ui.exams

import GetAllExams
import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.najih.android.dataClasses.Exam
import com.najih.android.ui.homePage.components.SearchBar
import com.najih.android.ui.uitilis.navbar
import io.ktor.client.HttpClient
import androidx.compose.runtime.setValue
import com.najih.android.ui.homePage.components.HomePage_navbar
import com.najih.android.ui.uitilis.BottomNavBar


@Composable
fun Exams (navController: NavController ,httpClient: HttpClient , context: Context) {

    // State to hold the fetched exams
    var exams by remember { mutableStateOf<List<Exam>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Fetch exams when the composable is displayed
    LaunchedEffect(Unit) {
        try {
            // Call your API to fetch exams
            val fetchedExams = GetAllExams(httpClient, context)
            exams = fetchedExams
        } catch (e: Exception) {
            // Handle error and set an error message if needed
            errorMessage = "Error fetching exams"
            Log.e("ExamsError", "Failed to fetch exams", e)
        } finally {
            isLoading = false
        }
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { navbar(navController) },
        bottomBar = { BottomNavBar(navController) }
    ) { innerPadding  ->
        Column(
            modifier = Modifier
                .background(Color(0xfff9f9f9))
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            if (isLoading) {
                Text("Loading exams...", modifier = Modifier.padding(top = 16.dp))
            } else if (errorMessage != null) {
                Text(errorMessage ?: "Unknown error", modifier = Modifier.padding(top = 16.dp))
            } else if (exams.isEmpty()) {
                Text("No exams available", modifier = Modifier.padding(top = 16.dp))
            } else {
                // Render the list of exams using LazyColumn
                Text(
                    text = "Exams",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(top = 16.dp, start = 11.dp)
                )

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    items(exams) { exam ->
                        ExamCard(navController,exam)
                    }
                }
            }
        }
    }


}


@Preview
@Composable
fun ExamsPreview() {
    val navController = rememberNavController()
//    val expExam = List<Exam>()
//    Exams(navController,expExam)
}