package com.najih.android.viewModels.Teachers

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.ktor.client.HttpClient



class TeachersViewModelFactory(
    private val httpClient: HttpClient,
    private val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TeachersViewModel::class.java)) {
            return TeachersViewModel(httpClient, context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

