package com.najih.android.ui.latestNews

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.najih.android.ui.uitilis.BottomNavBar
import com.najih.android.ui.uitilis.Navbar
import com.najih.android.util.GlobalFunctions
import com.najih.android.viewModels.News.LatestNewsViewModel
import com.najih.android.viewModels.News.LatestNewsViewModelFactory
import io.ktor.client.HttpClient
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.najih.android.dataClasses.NewsItem
import com.najih.android.ui.homePage.components.NewsCard

@Composable
fun LatestNews(
    navController: NavController,
    httpClient: HttpClient,
    context: Context
) {
    val viewModel: LatestNewsViewModel = viewModel(factory = LatestNewsViewModelFactory(httpClient, context))
    val newsList by viewModel.newsList.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val currentLanguage by remember { mutableStateOf(GlobalFunctions.getUserLanguage(context) ?: "en") }

    var selectedNewsItem by remember { mutableStateOf<NewsItem?>(null) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { Navbar(navController, backText = "stay up to date", titleText = "Latest News") },
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
            } else if (newsList.isEmpty()) {
                Text("No news available", modifier = Modifier.align(Alignment.CenterHorizontally))
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(newsList) { newsItem ->
                        val title = if (currentLanguage == "ar") newsItem.title.ar else newsItem.title.en
                        val description = if (currentLanguage == "ar") newsItem.des.ar else newsItem.des.en
                        val secureImageUrl = newsItem.image.url.replace("http://", "https://")

                        NewsCard(
                            headline = title,
                            description = description,
                            imagePainter = rememberAsyncImagePainter(secureImageUrl),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .clickable {
                                    selectedNewsItem = newsItem // Set selected news item when clicked
                                }
                        )
                    }
                }
            }
        }

        // This is where you call the NewsDetailsDialog composable within the composable scope
        selectedNewsItem?.let { newsItem ->
            NewsDetailsDialog(
                newsItem = newsItem,
                currentLanguage = currentLanguage,
                onDismiss = { selectedNewsItem = null }
            )
        }
    }
}
