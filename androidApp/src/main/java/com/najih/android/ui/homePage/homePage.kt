package com.najih.android.ui.homePage


import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.najih.android.ui.homePage.components.HomePageLatestNews
import com.najih.android.ui.homePage.components.HomePageTeachers
import com.najih.android.ui.homePage.components.Stages
import com.najih.android.ui.uitilis.BottomNavBar
import com.najih.android.ui.uitilis.HomeNavbar
import io.ktor.client.HttpClient


@Composable
fun HomePage(navController: NavController, httpClient: HttpClient,context: Context, modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { HomeNavbar(navController) },
        bottomBar = { BottomNavBar(navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()) // Enable vertical scrolling
        ) {
            // Display Categories first
            Stages(navController)
            // Display LatestNews
            HomePageLatestNews(navController,httpClient,context)
            // Display SuggestedLessons
            HomePageTeachers(navController,httpClient,context)
        }
    }
}





