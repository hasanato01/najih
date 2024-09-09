package com.najih.android.ui.homePage


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.najih.android.ui.homePage.components.HomePage_navbar
import com.najih.android.ui.homePage.components.LatestNews
import com.najih.android.ui.homePage.components.Stages
import com.najih.android.ui.homePage.components.SuggestedLessons


@Composable
fun HomePage(navController: NavController, modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { HomePage_navbar(navController) }
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
            LatestNews()
            // Display SuggestedLessons
            SuggestedLessons()
        }
    }
}





@Preview
@Composable
fun PreviewHomePage() {
    val navController = rememberNavController()
    // Pass the mock NavController to your composable
    HomePage(navController = navController)
}