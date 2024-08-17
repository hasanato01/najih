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
import com.najih.android.ui.homePage.components.latestNews
import com.najih.android.ui.homePage.components.suggestedLessons
import com.najih.android.ui.homePage.components.categories
import com.najih.android.ui.homePage.components.homePage_navbar



@Composable
fun HomePage(modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { homePage_navbar() }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding), // This avoids overlapping with the Navbar
            contentPadding = PaddingValues(bottom = 16.dp) // Add bottom padding if needed
        ) {
            // Display Categories first
            item {
                categories()
            }

            // Display LatestNews
            item {
                latestNews()
            }

            // Display SuggestedLessons
            item {
                suggestedLessons()
            }
        }
    }
}





@Preview
@Composable
fun PreviewHomePage() {
    HomePage()
}