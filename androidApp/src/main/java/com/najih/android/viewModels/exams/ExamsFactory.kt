package com.najih.android.viewModels.exams

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.ktor.client.HttpClient

class ExamViewModelFactory(private val httpClient: HttpClient) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ExamsViewModel::class.java) -> {
                ExamsViewModel(httpClient) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
