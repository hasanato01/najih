package com.najih.android.viewModels.userExams

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.ktor.client.HttpClient

class UserExamsViewModelFactory(private val httpClient: HttpClient) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserExamsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserExamsViewModel(httpClient) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
