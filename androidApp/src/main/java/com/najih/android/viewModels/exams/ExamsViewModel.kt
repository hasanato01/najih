package com.najih.android.viewModels.exams

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.najih.android.api.exams.getAllExams
import com.najih.android.dataClasses.Exam
import io.ktor.client.HttpClient
import kotlinx.coroutines.launch

class ExamsViewModel(private val httpClient: HttpClient) : ViewModel() {
    private val _exams = MutableLiveData<List<Exam>>(emptyList())
    val exams: LiveData<List<Exam>> = _exams

    private val _isLoading = MutableLiveData<Boolean>(true)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>(null)
    val errorMessage: LiveData<String?> = _errorMessage

    fun fetchExams(context: Context) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val fetchedExams = getAllExams(httpClient, context)
                _exams.value = fetchedExams
            } catch (e: Exception) {
                _errorMessage.value = "Error fetching exams"
                Log.e("ExamsError", "Failed to fetch exams", e)
            } finally {
                _isLoading.value = false
            }
        }
    }
}
