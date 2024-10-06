package com.najih.android.viewModels.Subjects

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.ktor.client.HttpClient

class SubjectsViewModelFactory(
    private val httpClient: HttpClient
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SubjectsViewModel::class.java)) {
            return SubjectsViewModel(httpClient) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
