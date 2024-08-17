package com.najih.android.ui.homePage


import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.najih.android.ui.homePage.components.Categories
import com.najih.android.ui.homePage.components.LatestNews
import com.najih.android.ui.homePage.components.Navbar
import com.najih.android.ui.homePage.components.SuggestedLessons


@Composable
fun HomePage(modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { Navbar() }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding), // This avoids overlapping with the Navbar
            contentPadding = PaddingValues(bottom = 16.dp) // Add bottom padding if needed
        ) {
            // Display Categories first
            item {
                Categories()
            }

            // Display LatestNews
            item {
                LatestNews()
            }

            // Display SuggestedLessons
            item {
                SuggestedLessons()
            }
        }
    }
}





@Preview
@Composable
fun PreviewHomePage() {
    HomePage()
}