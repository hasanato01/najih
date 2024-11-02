package com.najih.android.viewModels.News

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.ktor.client.HttpClient

class LatestNewsViewModelFactory(
    private val httpClient: HttpClient,
    private val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LatestNewsViewModel::class.java)) {
            return LatestNewsViewModel(httpClient, context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
