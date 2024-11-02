package com.najih.android.ui.homePage.components

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.najih.android.util.GlobalFunctions
import io.ktor.client.HttpClient

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.najih.android.viewModels.News.LatestNewsViewModel
import com.najih.android.viewModels.News.LatestNewsViewModelFactory

@Composable
fun HomePageLatestNews(
    navController:NavController,
    httpClient: HttpClient,
    context: Context,
) {
    val viewModel: LatestNewsViewModel = viewModel(factory = LatestNewsViewModelFactory(httpClient, context))
    val newsList by viewModel.newsList.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val currentLanguage by remember { mutableStateOf(GlobalFunctions.getUserLanguage(context) ?: "en") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Latest News",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.align(Alignment.CenterVertically)
            )

            TextButton(onClick = {
                navController.navigate("latest_news")
            }) {
                Text(
                    text = "See All →",
                    style = MaterialTheme.typography.labelMedium,
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else if (newsList.isEmpty()) {
            Text("No news available", modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                val limitedNewsListSize = minOf(5, newsList.size)
                items(limitedNewsListSize) { index ->
                    val newsItem = newsList[index]

                    // Get the title and description based on the current language
                    val title = if (currentLanguage == "ar") newsItem.title.ar else newsItem.title.en
                    val description = if (currentLanguage == "ar") newsItem.des.ar else newsItem.des.en
                    val secureImageUrl = newsItem.image.url.replace("http://", "https://")

                    NewsCard(
                        headline = title,
                        description = description,
                        imagePainter = rememberAsyncImagePainter(secureImageUrl)  // Use Coil for loading the image
                    )
                }
            }
        }
    }
}
