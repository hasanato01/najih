package com.najih.android.ui.exams


import android.content.Context
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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import io.ktor.client.HttpClient
import androidx.lifecycle.viewmodel.compose.viewModel
import com.najih.android.ui.uitilis.BottomNavBar
import com.najih.android.ui.uitilis.HomeNavbar
import com.najih.android.ui.uitilis.Navbar
import com.najih.android.viewModels.exams.ExamViewModelFactory
import com.najih.android.viewModels.exams.ExamsViewModel


@Composable
fun Exams (navController: NavController ,httpClient: HttpClient , context: Context) {

    val viewModelFactory = ExamViewModelFactory(httpClient)
    val examsViewModel: ExamsViewModel = viewModel(factory = viewModelFactory)

    // Observe the state from the ViewModel
    val exams by examsViewModel.exams.observeAsState(emptyList())
    val isLoading by examsViewModel.isLoading.observeAsState(true)
    val errorMessage by examsViewModel.errorMessage.observeAsState(null)

    // Fetch exams when the composable is displayed
    LaunchedEffect(Unit) {
        examsViewModel.fetchExams(context)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { Navbar(navController  , backText = "test your self" , titleText = "Exams" ) },
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


