package com.najih.android.ui.exams


import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import io.ktor.client.HttpClient
import androidx.lifecycle.viewmodel.compose.viewModel
import com.najih.android.R
import com.najih.android.ui.uitilis.BottomNavBar
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
        topBar = { Navbar(navController, backText = stringResource(R.string.test_your_self), titleText = stringResource(R.string.exams)) },
        bottomBar = { BottomNavBar(navController) }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .background(Color(0xfff9f9f9))
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top
            ) {


                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .size(40.dp)
                            .padding(8.dp),
                        color = colorResource(id = R.color.secondColor),
                        strokeWidth = 4.dp
                    )
                } else if (errorMessage != null) {
                    Text(
                        text = errorMessage ?: "Unknown error",
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(16.dp)
                    )
                } else if (exams.isEmpty()) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.books),
                            contentDescription = stringResource(R.string.no_exams_available),
                            modifier = Modifier.size(100.dp)
                        )
                        Text(
                            text = stringResource(R.string.no_exams_available),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        items(exams) { exam ->
                            ExamCard(navController, exam)
                        }
                    }
                }
            }
        }
    }



}
