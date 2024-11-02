package com.najih.android.ui.examResults

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.najih.android.R
import com.najih.android.api.exams.getUserExams
import com.najih.android.dataClasses.SubmitExamRequest
import com.najih.android.ui.homePage.components.SearchBar
import com.najih.android.ui.uitilis.BottomNavBar
import com.najih.android.ui.uitilis.HomeNavbar
import com.najih.android.ui.uitilis.Navbar
import com.najih.android.viewModels.userExams.UserExamsViewModel
import com.najih.android.viewModels.userExams.UserExamsViewModelFactory
import io.ktor.client.HttpClient


@Composable
fun UserExams (navController: NavController, httpClient: HttpClient, context: Context) {

    val viewModelFactory = UserExamsViewModelFactory(httpClient)
    val userExamsViewModel: UserExamsViewModel = viewModel(factory = viewModelFactory)

    val examResults by userExamsViewModel.examResults.observeAsState(emptyList())
    val isLoading by userExamsViewModel.isLoading.observeAsState(true)
    val errorMessage by userExamsViewModel.errorMessage.observeAsState(null)

    LaunchedEffect(Unit) {
        userExamsViewModel.fetchUserExams(context)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { Navbar(navController, backText = stringResource(R.string.submitted_exams), titleText = stringResource(R.string.my_exams)) },
        bottomBar = { BottomNavBar(navController) }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .background(Color(0xfff9f9f9))
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

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
                    modifier = Modifier.padding(top = 16.dp)
                )
            } else if (examResults.isEmpty()) {
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
                    items(examResults) { examResult ->
                        ExamResultsCard(navController, examResult)
                    }
                }
            }
        }
    }

}