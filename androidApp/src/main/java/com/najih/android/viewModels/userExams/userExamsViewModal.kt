package com.najih.android.viewModels.userExams
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.najih.android.api.exams.getUserExams
import com.najih.android.dataClasses.SubmitExamRequest
import io.ktor.client.HttpClient
import kotlinx.coroutines.launch

class UserExamsViewModel(private val httpClient: HttpClient) : ViewModel() {
    private val _examResults = MutableLiveData<List<SubmitExamRequest>>(emptyList())
    val examResults: LiveData<List<SubmitExamRequest>> = _examResults

    private val _isLoading = MutableLiveData<Boolean>(true)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>(null)
    val errorMessage: LiveData<String?> = _errorMessage

    fun fetchUserExams(context: Context) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val fetchedExamResults = getUserExams(httpClient, context)
                _examResults.value = fetchedExamResults
            } catch (e: Exception) {
                _errorMessage.value = "Error fetching exam results"
                Log.e("ExamsError", "Failed to fetch exams", e)
            } finally {
                _isLoading.value = false
            }
        }
    }
}
