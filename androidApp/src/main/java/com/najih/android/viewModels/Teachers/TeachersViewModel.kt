package com.najih.android.viewModels.Teachers

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.najih.android.api.teachers.getAllTeachers
import com.najih.android.dataClasses.Teacher
import io.ktor.client.HttpClient
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TeachersViewModel(private val httpClient: HttpClient, private val context: Context) : ViewModel() {
    private val _teachersList = MutableStateFlow<List<Teacher>>(emptyList())
    val teachersList: StateFlow<List<Teacher>> get() = _teachersList

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    init {
        fetchTeachers()
    }

    private fun fetchTeachers() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _isLoading.value = true
                val teachers = getAllTeachers(httpClient, context) // Fetch teachers from API
                _teachersList.value = teachers
            } catch (e: Exception) {
                Log.e("TeachersViewModel", "Failed to fetch teachers: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }
}
