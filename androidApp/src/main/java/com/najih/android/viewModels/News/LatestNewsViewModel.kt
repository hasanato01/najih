package com.najih.android.viewModels.News

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.najih.android.api.news.getLatestNews
import com.najih.android.dataClasses.NewsItem
import io.ktor.client.HttpClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LatestNewsViewModel(private val httpClient: HttpClient, private val context: Context) : ViewModel() {
    private val _newsList = MutableStateFlow<List<NewsItem>>(emptyList())
    val newsList: StateFlow<List<NewsItem>> get() = _newsList

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    init {
        fetchLatestNews()
    }

    private fun fetchLatestNews() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _isLoading.value = true
                val news = getLatestNews(httpClient, context) // Fetch news from API
                _newsList.value = news
            } catch (e: Exception) {
                Log.e("LatestNewsViewModel", "Failed to fetch latest news: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }
}
